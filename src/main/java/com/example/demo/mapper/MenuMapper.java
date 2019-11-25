package com.example.demo.mapper;

import com.example.demo.model.Menu;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.data.annotation.Id;

import java.sql.JDBCType;
import java.util.List;

public interface MenuMapper {
    /**
     * 获取所有菜单
     */
    @Select("select menu_id, parent_id, menu_name, url, perms, order_num, create_time, modify_time from menu order by order_num")
    @Results(id = "menuMap",value = {
        @Result(column="menu_id", jdbcType= JdbcType.INTEGER, property="menuId",id = true),
        @Result(column="parent_id", jdbcType= JdbcType.INTEGER, property="parentId"),
        @Result(column="menu_name", jdbcType= JdbcType.VARCHAR, property="menuName"),
        @Result(column="url", jdbcType= JdbcType.VARCHAR, property="url"),
        @Result(column="perms", jdbcType= JdbcType.VARCHAR, property="perms"),
        @Result(column="order_num", jdbcType= JdbcType.INTEGER, property="orderNum"),
        @Result(column="create_time", jdbcType= JdbcType.TIMESTAMP, property="createTime"),
        @Result(column="modify_time", jdbcType= JdbcType.TIMESTAMP, property="modifyTime")
    })
    List<Menu> selectAll();
    @Select("select distinct\n" +
            "               menu.menu_id,\n" +
            "               menu.parent_id,\n" +
            "               menu.menu_name,\n" +
            "               menu.url,\n" +
            "               menu.perms,\n" +
            "               menu.order_num\n" +
            "        from\n" +
            "             user,\n" +
            "             role,\n" +
            "             user_role,\n" +
            "             menu,\n" +
            "             role_menu\n" +
            "        where\n" +
            "            user.user_id = user_role.user_id\n" +
            "          and\n" +
            "            role.role_id = user_role.role_id\n" +
            "          and\n" +
            "            role.role_id = role_menu.role_id\n" +
            "          and\n" +
            "            menu.menu_id = role_menu.menu_id\n" +
            "          and\n" +
            "            user.username = #{userName}\n" +
            "        order by order_num")
    @ResultMap("menuMap")
    List<Menu> selectMenuByUserName(@Param("userName") String username);
}
