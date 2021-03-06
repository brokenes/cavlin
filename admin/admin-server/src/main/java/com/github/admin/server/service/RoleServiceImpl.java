package com.github.admin.server.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.admin.common.constants.Constants;
import com.github.admin.common.domain.Role;
import com.github.admin.common.request.RoleRequest;
import com.github.admin.common.service.RoleService;
import com.github.admin.server.dao.RoleDao;
import com.github.admin.server.dao.RolePermissionDao;
import com.github.admin.server.dao.UserRoleDao;
import com.github.appmodel.domain.result.ModelResult;
import com.github.appmodel.page.DataPage;
import com.github.appmodel.vo.PageVo;

@Service
public class RoleServiceImpl extends BaseService implements RoleService{
	
	private static Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private UserRoleDao userRoleDao;
	
	@Autowired
	private RolePermissionDao rolePermissionDao;
	
	public ModelResult<List<Role>> selectRoleByUserId(Integer userId){
		ModelResult<List<Role>> modelResult = new ModelResult<List<Role>>();
		logger.info("根据userId = 【{}】查询用户对应的角色",userId);
		if(userId == null) {
			logger.error("当前userId为空！");
			modelResult.withError(Constants.FAIL_MSG_CODE,Constants.QUERY_FAIL_MSG);
			return modelResult;
		}
		List<Role> list = roleDao.selectRoleByUserId(userId);
		modelResult.setModel(list);
		return modelResult;
	}

	@Override
	public ModelResult<PageVo> pageRoleList(RoleRequest roleRequest) {
		ModelResult<PageVo> modelResult = new ModelResult<PageVo>();
		PageVo pageVo = new PageVo();
		DataPage<Role> dataPage = new DataPage<Role>();
		this.setDataPage(dataPage, roleRequest);
		int start = dataPage.getStartIndex();
		int offset = dataPage.getPageSize();
		long totalCount = roleDao.pageRoleListCount();
		List<Role> result = roleDao.pageRoleList(start,offset);
		pageVo.setTotal(totalCount);
		pageVo.setRows(result);
		modelResult.setModel(pageVo);
		return modelResult;
	}

	@Override
	public ModelResult<List<Role>> allRolesList() {
		ModelResult<List<Role>>  modelResult = new ModelResult<List<Role>> ();
		List<Role> list = roleDao.allRolesList();
		modelResult.setModel(list);
		return modelResult;
	}

	@Override
	public ModelResult<Integer> insertSelective(Role role) {
		ModelResult<Integer> modelResult = new ModelResult<Integer>();
		if(role == null || StringUtils.isBlank(role.getName())){
			logger.error("添加角色为空或者角色名称为空");
			modelResult.withError(Constants.FAIL_MSG_CODE, "角色信息为空或角色名称为空");
			return modelResult;
		}
		String roleName = role.getName();
		logger.info("添加角色名称roleName = 【{}】",roleName);
		Role existRole = roleDao.selectRoleByRoleName(roleName);
		if(existRole != null) {
			modelResult.withError(Constants.FAIL_MSG_CODE, "角色名称已经存在！");
			return modelResult;
		}
		int result = roleDao.insertSelective(role);
		if(result > 0) {
			modelResult.setModel(result);
		}else {
			modelResult.withError(Constants.FAIL_MSG_CODE,Constants.ADD_FAIL_MSG);
		}
		return modelResult;
	}

	@Override
	public ModelResult<Role> selectByPrimaryKey(Integer roleId) {
		ModelResult<Role> modelResult = new ModelResult<Role>();
		if(roleId == null || roleId == 0) {
			modelResult.withError(Constants.FAIL_MSG_CODE, "角色Id为空或者为0");
			return modelResult;
		}
		Role role = roleDao.selectByPrimaryKey(roleId);
		modelResult.setModel(role);
		return modelResult;
	}

	@Override
	public ModelResult<Integer> updateByPrimaryKeySelective(Role role) {
		ModelResult<Integer> modelResult = new ModelResult<Integer>();
		if(role == null || role.getRoleId() == null || role.getRoleId() == 0 || StringUtils.isBlank(role.getName())) {
			logger.error("修改角色为空或者角色名称为空");
			modelResult.withError(Constants.FAIL_MSG_CODE, "角色信息为空或角色相关信息为空");
			return modelResult;
		}
		int result = roleDao.updateByPrimaryKeySelective(role);
		if(result > 0) {
			modelResult.setModel(result);
		}else {
			modelResult.withError(Constants.FAIL_MSG_CODE,Constants.UPDATE_FAIL_MSG);
		}
		return modelResult;
	}

	@Override
	@Transactional
	public ModelResult<Integer> deleteByPrimaryKeys(String roleIds) {
		ModelResult<Integer> modelResult = new ModelResult<Integer>();
		if(StringUtils.isBlank(roleIds)) {
			modelResult.withError(Constants.FAIL_MSG_CODE, "角色id为空");
			return modelResult;
		}
		String[] idArray = roleIds.split("-");
		int result = 0;
		for (String idStr : idArray) {
			if (StringUtils.isBlank(idStr)) {
				continue;
			}
			Integer roleId = Integer.parseInt(idStr);
			userRoleDao.deleteByRoleId(roleId);
			rolePermissionDao.deleteByRoleId(roleId);
			result += roleDao.deleteByPrimaryKey(roleId);
		}
		if(result > 0) {
			modelResult.setModel(result);
		}else {
			modelResult.withError(Constants.FAIL_MSG_CODE,Constants.DELETE_FAIL_MSG);
		}
		return modelResult;
	}

}
