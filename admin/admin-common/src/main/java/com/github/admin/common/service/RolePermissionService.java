package com.github.admin.common.service;

import com.alibaba.fastjson.JSONArray;
import com.github.appmodel.domain.result.ModelResult;

public interface RolePermissionService {

	
	ModelResult<Integer> rolePermission(JSONArray datas, Integer roleId);

}
