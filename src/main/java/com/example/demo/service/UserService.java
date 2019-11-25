package com.example.demo.service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Menu;
import com.example.demo.model.User;
import com.example.demo.utils.TreeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private MenuService menuService;
    public Set<String> selectRoleNameByUserName(String username) {
        return userMapper.selectRoleNameByUserName(username);
    }
    public Set<String> selectPermsByUsername(String username) {
        Set<String> perms = new HashSet<>();
        List<Menu> menuTreeVOS = menuService.selectMenuTreeVOByUsername(username);
        List<Menu> leafNodeMenuList = TreeUtil.getAllLeafNode(menuTreeVOS);
        for (Menu menu : leafNodeMenuList) {
            perms.add(menu.getPerms());
        }
        perms.addAll(userMapper.selectOperatorPermsByUserName(username));
        return perms;
    }
    public User selectOneByUserName(String username) {
        return userMapper.selectOneByUserName(username);
    }

}
