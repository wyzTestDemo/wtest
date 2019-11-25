package com.example.demo.mapper;

import com.example.demo.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import java.util.Set;

public interface UserMapper {
    @Select("select role.role_name from  user,role,user_role where user.user_id = user_role.user_id and role.role_id = user_role.role_id and user.username = #{username,jdbcType=VARCHAR}")
    Set<String> selectRoleNameByUserName(@Param("username") String username);

    @Select(" select operator.perms\n" +
            "        from\n" +
            "            user,\n" +
            "            role,\n" +
            "            user_role,\n" +
            "            operator,\n" +
            "            role_operator\n" +
            "        where\n" +
            "            user.user_id = user_role.user_id\n" +
            "          and\n" +
            "            role.role_id = user_role.role_id\n" +
            "          and\n" +
            "            role.role_id = role_operator.role_id\n" +
            "          and\n" +
            "            operator.operator_id = role_operator.operator_id\n" +
            "          and\n" +
            "            user.username = #{username, jdbcType=VARCHAR}")
    Set<String> selectOperatorPermsByUserName(@Param("username") String username);

    @Select(" select\n" +
            "user_id,\n" +
            "        username,\n" +
            "        password,\n" +
            "        salt,\n" +
            "        email,\n" +
            "        status,\n" +
            "        last_login_time,\n" +
            "        create_time,\n" +
            "        modify_time,\n" +
            "        active_code" +
            "        from user\n" +
            "        where username=#{username,jdbcType=VARCHAR} limit 1")
    @Results(id = "user", value = {
            @Result(column = "user_id", jdbcType = JdbcType.INTEGER, property = "userId", id = true),
            @Result(column = "username", jdbcType = JdbcType.VARCHAR, property = "username"),
            @Result(column = "password", jdbcType = JdbcType.VARCHAR, property = "password"),
            @Result(column = "salt", jdbcType = JdbcType.VARCHAR, property = "salt"),
            @Result(column = "email", jdbcType = JdbcType.VARCHAR, property = "email"),
            @Result(column = "status", jdbcType = JdbcType.CHAR, property = "status"),
            @Result(column = "last_login_time", jdbcType = JdbcType.TIMESTAMP, property = "lastLoginTime"),
            @Result(column = "create_time", jdbcType = JdbcType.TIMESTAMP, property = "createTime"),
            @Result(column = "modify_time", jdbcType = JdbcType.TIMESTAMP, property = "modifyTime")
    })
    User selectOneByUserName(@Param("username") String username);
}
