package com.example.demo.mapper;

import com.example.demo.model.Operator;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface OperatorMapper {
    @Select("select operator_id, menu_id, operator_name, url, perms, http_method, create_time, modify_time from operator")
    @Results(id = "operator",value = {
            @Result(column="operator_id", jdbcType= JdbcType.INTEGER, property="operatorId",id = true),
            @Result(column="menu_id", jdbcType=JdbcType.INTEGER, property="menuId"),
            @Result(column="operator_name", jdbcType=JdbcType.VARCHAR, property="operatorName"),
            @Result(column="url", jdbcType=JdbcType.VARCHAR, property="url"),
            @Result(column="perms", jdbcType=JdbcType.VARCHAR, property="perms"),
            @Result(column="http_method", jdbcType=JdbcType.VARCHAR, property="httpMethod"),
            @Result(column="create_time", jdbcType=JdbcType.TIMESTAMP, property="createTime"),
            @Result(column="modify_time", jdbcType=JdbcType.TIMESTAMP, property="modifyTime")
    })
    List<Operator> selectAll();
}
