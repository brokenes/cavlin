package com.github.admin.server.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.github.admin.common.domain.System;

@Repository
public interface SystemDao {

	public long pageSystemListCount();

	public List<System> pageSystemList(@Param("start")int start, @Param("offset")int offset);

	public int insertSelective(System system);
	
	public Integer deleteByPrimaryKey(@Param("systemId")String systemId);

	public System selectByPrimaryKey(@Param("systemId")Integer systemId);

	public int updateByPrimaryKeySelective(System system);

	public List<System> querySystemByStatus(@Param("status")Integer status);
	
	
}
