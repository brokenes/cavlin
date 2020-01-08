package com.github.pattern.server.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.github.pattern.common.domain.Agent;

@Repository
public interface AgentDao {
	
    int deleteByPrimaryKey(@Param("agentId")Integer agentId);

    int insert(Agent record);

    int insertSelective(Agent record);

    Agent selectByPrimaryKey(@Param("agentId")Integer agentId);

    int updateByPrimaryKeySelective(Agent record);

    int updateByPrimaryKey(Agent record);
    
    
    long pageCount(@Param("statusList")List<Integer> status);
    
    List<Agent> pageList(@Param("start")int start, @Param("offset")int offset,@Param("statusList")List<Integer> status);
}