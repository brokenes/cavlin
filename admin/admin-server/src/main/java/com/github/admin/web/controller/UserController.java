package com.github.admin.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.admin.common.domain.User;
import com.github.admin.common.request.UserRequest;
import com.github.admin.common.service.UserService;
import com.github.appmodel.domain.result.ModelResult;
import com.github.appmodel.vo.PageVo;

@RestController
@RequestMapping("/admin/server/user")
public class UserController {

	@Autowired
	private UserService userServiceImpl;
	
	
	@GetMapping("/selectUserByUserName/{userName}")
	ModelResult<User> selectUserByUserName(@PathVariable("userName")String userName){
		return userServiceImpl.selectUserByUserName(userName);
	}
	
	@PostMapping("/pageUserInfoList")
	ModelResult<PageVo> pageUserInfoList(@RequestBody UserRequest userRequest){
		return userServiceImpl.pageUserInfoList(userRequest);
	}
	
	@PostMapping("/insertSelective")
	ModelResult<Integer> insertSelective(@RequestBody User user){
		return userServiceImpl.insertSelective(user);
	}

	@GetMapping("/selectByPrimaryKey/{userId}")
	ModelResult<User> selectByPrimaryKey(@PathVariable("userId") Integer userId){
		return userServiceImpl.selectByPrimaryKey(userId);
	}
	
	@PostMapping("/updateByPrimaryKey")
	ModelResult<Integer> updateByPrimaryKey(@RequestBody User user){
		return userServiceImpl.updateByPrimaryKey(user);
	}
	
	@PostMapping("/deleteByPrimaryKey/{ids}")
	ModelResult<Integer> deleteByPrimaryKey(@PathVariable("ids") String ids){
		return userServiceImpl.deleteByPrimaryKey(ids);
	}
}
