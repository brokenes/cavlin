package com.github.admin.server.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.admin.common.constants.Constants;
import com.github.admin.common.domain.Organization;
import com.github.admin.common.request.OrganizationRequest;
import com.github.admin.common.service.OrganizationService;
import com.github.admin.server.dao.OrganizationDao;
import com.github.appmodel.domain.result.ModelResult;
import com.github.appmodel.page.DataPage;
import com.github.appmodel.vo.PageVo;


@Service
public class OrganizationServiceImpl extends BaseService implements OrganizationService{

	@Autowired
	private OrganizationDao organizationDao;
	
	
	@Override
	public ModelResult<PageVo> pageOrganizationList(OrganizationRequest organizationRequest) {
		ModelResult<PageVo> modelResult = new ModelResult<PageVo>();
		PageVo pageVo = new PageVo();
		DataPage<Organization> dataPage = new DataPage<Organization>();
		this.setDataPage(dataPage, organizationRequest);
		int start = dataPage.getStartIndex();
		int offset = dataPage.getPageSize();
		long totalCount = organizationDao.pageOrganizationListCount();
		List<Organization> result = organizationDao.pageOrganizationList(start,offset);
		pageVo.setTotal(totalCount);
		pageVo.setRows(result);
		modelResult.setModel(pageVo);
		return modelResult;
	}


	@Override
	public ModelResult<Integer> insertSelective(Organization organization) {
		ModelResult<Integer> modelResult = new ModelResult<Integer>();
		int result = organizationDao.insertSelective(organization);
		if(result > 0) {
			modelResult.setModel(result);
		}else {
			modelResult.withError(Constants.FAIL_MSG_CODE, Constants.ADD_FAIL_MSG);
		}
		return modelResult;
	}


	@Override
	public ModelResult<Organization> selectByPrimaryKey(Integer organizationId) {
		ModelResult<Organization> modelResult = new ModelResult<Organization>();
		if(organizationId == null || organizationId == 0) {
			modelResult.withError(Constants.FAIL_MSG_CODE, Constants.QUERY_FAIL_MSG);
			return modelResult;
		}
		
		Organization organization = organizationDao.selectByPrimaryKey(organizationId);
		modelResult.setModel(organization);
		return modelResult;
	}


	@Override
	public ModelResult<Integer> updateByPrimaryKeySelective(Organization organization) {
		ModelResult<Integer> modelResult = new ModelResult<Integer>();
		if(organization == null || organization.getOrganizationId() == null) {
			modelResult.withError(Constants.FAIL_MSG_CODE, Constants.UPDATE_FAIL_MSG);
			return modelResult;
		}
		
		int result = organizationDao.updateByPrimaryKeySelective(organization);
		if(result > 0) {
			modelResult.setModel(result);
		}else {
			modelResult.withError(Constants.FAIL_MSG_CODE, Constants.UPDATE_FAIL_MSG);
		}
		return modelResult;
	}


	@Override
	public ModelResult<Integer> deleteByPrimaryKey(String organizationId) {
		ModelResult<Integer> modelResult = new ModelResult<Integer>();
		if(StringUtils.isEmpty(organizationId)) {
			modelResult.withError(Constants.FAIL_MSG_CODE, Constants.DELETE_FAIL_MSG);
			return modelResult;
		}
		
		int result = organizationDao.deleteByPrimaryKey(organizationId);
		if(result > 0) {
			modelResult.setModel(result);
		}else {
			modelResult.withError(Constants.FAIL_MSG_CODE, Constants.DELETE_FAIL_MSG);
		}
		return modelResult;
	}


	@Override
	public ModelResult<List<Organization>> allOrganizationList() {
		ModelResult<List<Organization>> modelResult = new ModelResult<List<Organization>>();
		List<Organization> list = organizationDao.allOrganizationList();
		modelResult.setModel(list);
		return modelResult;
	}
	
	
	

}
