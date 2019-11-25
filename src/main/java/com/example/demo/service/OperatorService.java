package com.example.demo.service;

import com.example.demo.mapper.OperatorMapper;
import com.example.demo.model.Operator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OperatorService {
    @Resource
    private OperatorMapper operatorMapper;
    public List<Operator> selectAll(){
        return operatorMapper.selectAll();
    }
}
