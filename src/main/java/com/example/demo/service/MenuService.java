package com.example.demo.service;

import com.example.demo.mapper.MenuMapper;
import com.example.demo.model.Menu;
import com.example.demo.utils.ShiroUtil;
import com.example.demo.utils.TreeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MenuService {
    /**
     * 获取指定用户拥有的树形菜单 (admin 账户拥有所有权限.)
     */
    @Resource
    private MenuMapper menuMapper;
    public List<Menu> selectMenuTreeVOByUsername(String username) {
        List<Menu> menus;
        if (ShiroUtil.getSuperAdminUsername().equals(username)) {
            menus = menuMapper.selectAll();
        } else {
            menus = menuMapper.selectMenuByUserName(username);
        }
        return toTree(menus);
    }
    public List<Menu> getLeafNodeMenu() {
        List<Menu> allMenuTreeVO = getALLMenuTree();
        return TreeUtil.getAllLeafNode(allMenuTreeVO);
    }
    /**
     * 获取所有菜单
     */
    public List<Menu> selectAll() {
        return menuMapper.selectAll();
    }
    /**
     * 获取所有菜单 (树形结构)
     */
    public List<Menu> getALLMenuTree() {
        List<Menu> menus = selectAll();
        return toTree(menus);
    }

    /**
     * 转换为树形结构
     */
    private List<Menu> toTree(List<Menu> menuList) {
        return TreeUtil.toTree(menuList, "menuId", "parentId", "children", Menu.class);
    }
}
