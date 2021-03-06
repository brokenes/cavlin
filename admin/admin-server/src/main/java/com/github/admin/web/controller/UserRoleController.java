package com.github.admin.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.admin.common.domain.UserRole;
import com.github.admin.common.service.UserRoleService;
import com.github.appmodel.domain.result.ModelResult;

@RestController
@RequestMapping("/admin/server/userRole")
public class UserRoleController {

	@Autowired
	private UserRoleService userRoleServiceImpl;
	
	
	@GetMapping("/selectByUserId/{userId}")
	ModelResult<List<UserRole>> selectByUserId(@PathVariable("userId") Integer userId){
		return userRoleServiceImpl.selectByUserId(userId);
	}
	@GetMapping("/role/{roleIds}/{userId}")
	ModelResult<Integer> role(@PathVariable("roleIds")String[] roleIds, @PathVariable("userId")Integer userId){
		return userRoleServiceImpl.role(roleIds,userId);
	}
}
