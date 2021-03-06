package com.github.admin.server.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.admin.common.constants.Constants;
import com.github.admin.common.domain.System;
import com.github.admin.common.request.SystemRequest;
import com.github.admin.common.service.SystemService;
import com.github.admin.server.dao.SystemDao;
import com.github.appmodel.domain.result.ModelResult;
import com.github.appmodel.page.DataPage;
import com.github.appmodel.vo.PageVo;


@Service
public class SystemServiceImpl extends BaseService implements SystemService{

	private static Logger logger = LoggerFactory.getLogger(SystemServiceImpl.class);
	
	@Autowired
	private SystemDao systemDao;
	
	@Override
	public ModelResult<PageVo> pageSystemList(SystemRequest systemRequest) {
		ModelResult<PageVo> modelResult = new ModelResult<PageVo>();
		DataPage<System> dataPage = new DataPage<System>();
		PageVo pageVo = new PageVo();
		this.setDataPage(dataPage, systemRequest);;
		int start = dataPage.getStartIndex();
		int offset = dataPage.getPageSize();
		long totalCount = systemDao.pageSystemListCount();
		List<System> result = systemDao.pageSystemList(start,offset);
		pageVo.setTotal(totalCount);
		pageVo.setRows(result);
		modelResult.setModel(pageVo);
		return modelResult;
	}

	@Override
	public ModelResult<Integer> insertSelective(System system) {
		ModelResult<Integer> modelResult = new ModelResult<Integer>();
		int result = systemDao.insertSelective(system);
		logger.info("添加系统操作返回结果result = 【{}】",result);
		if(result > 0) {
			modelResult.setModel(result);
		}else {
			modelResult.withError(Constants.FAIL_MSG_CODE,Constants.ADD_FAIL_MSG);
		}
		return modelResult;
	}

	@Override
	@Transactional
	public ModelResult<Integer> deleteByPrimaryKey(String ids) {
		ModelResult<Integer> modelResult = new ModelResult<Integer>();
		logger.info("删除系统操作 ids = 【{}】",ids);
		Integer result = 0;
		if(StringUtils.isNotBlank(ids)) {
			String[] systemIds = ids.split("-");
			for(String systemId:systemIds) {
				result += systemDao.deleteByPrimaryKey(systemId);
			}
		}
		logger.info("删除系统操作返回结果 result = 【{}】",result);
		if(result > 0) {
			modelResult.setModel(result);
		}else {
			modelResult.withError(Constants.FAIL_MSG_CODE,Constants.DELETE_FAIL_MSG);
		}
		return modelResult;
	}

	@Override
	public ModelResult<System> selectByPrimaryKey(Integer systemId) {
		logger.info("查询系统对象systemId = 【{}】",systemId);
		ModelResult<System> modelResult = new ModelResult<System>();
		if(systemId == null) {
			logger.info("查询系统对象systemId为空！");
			modelResult.withError(Constants.FAIL_MSG_CODE, Constants.QUERY_FAIL_MSG);
		}else {
			System system = systemDao.selectByPrimaryKey(systemId);
			modelResult.setModel(system);
		}
		return modelResult;
	}

	@Override
	public ModelResult<Integer> updateByPrimaryKeySelective(System system) {
		ModelResult<Integer> modelResult = new ModelResult<Integer>();
		if(system == null || system.getSystemId() == null) {
			modelResult.withError(Constants.FAIL_MSG_CODE,Constants.UPDATE_FAIL_MSG);
			return modelResult;
		}
		int result = systemDao.updateByPrimaryKeySelective(system);
		logger.info("编辑系统对象返回结果result = 【{}】",result);
		if(result > 0) {
			modelResult.setModel(result);
		}else {
			modelResult.withError(Constants.FAIL_MSG_CODE,Constants.UPDATE_FAIL_MSG);
		}
		return modelResult;
	}

	@Override
	public ModelResult<List<System>> querySystemByStatus(Integer status) {
		ModelResult<List<System>> modelResult = new ModelResult<List<System>>();
		if(status == null) {
			modelResult.withError(Constants.FAIL_MSG_CODE,"状态为非法参数");
			return modelResult;
		}
		List<System> list = systemDao.querySystemByStatus(status);
		modelResult.setModel(list);
		return modelResult;
	}

}
