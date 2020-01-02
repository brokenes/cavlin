package com.github.admin.server.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleDao {

	int deleteByRoleId(@Param("roleId") Integer roleId);

}