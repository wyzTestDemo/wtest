package com.example.demo.service;

import com.example.demo.model.Menu;
import com.example.demo.model.Operator;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShiroService {

    private static final Logger log = LoggerFactory.getLogger(ShiroService.class);

    @Lazy
    @Resource
    private ShiroFilterFactoryBean shiroFilterFactoryBean;

    @Resource
    private MenuService menuService;

    @Resource
    private OperatorService operatorService;

    /**
     * 从数据库加载用户拥有的菜单权限和 API 权限.
     */
    public Map<String, String> getUrlPermsMap() {
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        // 系统默认过滤器
        filterChainDefinitionMap.put("/favicon.ico", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");
        filterChainDefinitionMap.put("/images/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/lib/**", "anon");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/register", "anon");
        // 验证码
        filterChainDefinitionMap.put("/captcha", "anon");
        // 检查用户名是否存在
        filterChainDefinitionMap.put("/checkUser", "anon");

        List<Menu> menuList = menuService.getLeafNodeMenu();
        for (Menu menu : menuList) {
            String url = menu.getUrl();
            if (url != null) {
                String perms = "perms[" + menu.getPerms() + "]";
                filterChainDefinitionMap.put(url, perms);
            }
        }

        List<Operator> operatorList = operatorService.selectAll();
        for (Operator operator : operatorList) {
            String url = operator.getUrl();
            if (url != null) {
                if (operator.getHttpMethod() != null
                        && !"".equals(operator.getHttpMethod())) {
                    url += ("==" + operator.getHttpMethod());
                }
                String perms = "perms[" + operator.getPerms() + "]";
                filterChainDefinitionMap.put(url, perms);
            }
        }
        /*可以在这里设置，避免被覆盖*/
        filterChainDefinitionMap.put("/userIndex","anon");
        filterChainDefinitionMap.put("/myUserIndex","anon");
        filterChainDefinitionMap.put("/**", "authc");

        return filterChainDefinitionMap;
    }

    /**
     * 更新 Shiro 过滤器链
     */
    public void updateFilterChain() {
        synchronized (shiroFilterFactoryBean) {
            AbstractShiroFilter shiroFilter;
            try {
                shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean
                        .getObject();
            } catch (Exception e) {
                throw new RuntimeException(
                        "get ShiroFilter from shiroFilterFactoryBean error!");
            }
            PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter
                    .getFilterChainResolver();
            DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver
                    .getFilterChainManager();
            // 清空老的权限控制
            manager.getFilterChains().clear();
            shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
            shiroFilterFactoryBean
                    .setFilterChainDefinitionMap(getUrlPermsMap());
            // 重新构建生成
            Map<String, String> chains = shiroFilterFactoryBean
                    .getFilterChainDefinitionMap();
            for (Map.Entry<String, String> entry : chains.entrySet()) {
                String url = entry.getKey();
                String chainDefinition = entry.getValue().trim()
                        .replace(" ", "");
                manager.createChain(url, chainDefinition);
            }
            log.info("更新 Shiro 过滤器链");
        }
    }
}