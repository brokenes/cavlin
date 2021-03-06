package com.github.admin.client.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.admin.common.domain.Organization;
import com.github.admin.common.request.OrganizationRequest;
import com.github.appmodel.domain.result.ModelResult;
import com.github.appmodel.vo.PageVo;


@FeignClient(name="admin-server")
@RequestMapping("/admin/server/organization")
public interface OrganizationServiceClient {

	@PostMapping("/pageOrganizationList")
	ModelResult<PageVo> pageOrganizationList(@RequestBody OrganizationRequest organizationRequest);

	@PostMapping("/insertSelective")
	public ModelResult<Integer> insertSelective(@RequestBody Organization organization);

	@GetMapping("selectByPrimaryKey/{organizationId}")
	public ModelResult<Organization> selectByPrimaryKey(@PathVariable("organizationId")Integer organizationId);

	@PostMapping("/updateByPrimaryKeySelective")
	public ModelResult<Integer> updateByPrimaryKeySelective(@RequestBody Organization organization);

	@GetMapping("/deleteByPrimaryKeys/{organizationId}")
	public ModelResult<Integer> deleteByPrimaryKeys(@PathVariable("organizationId") String organizationId);

	@PostMapping("/allOrganizationList")
	public ModelResult<List<Organization>> allOrganizationList();
	
}
