package com.github.admin.common.domain;

public class UserOrganization extends  BaseObject {
    private Integer userOrganizationId;

    private Integer userId;

    private Integer organizationId;

    public Integer getUserOrganizationId() {
        return userOrganizationId;
    }

    public void setUserOrganizationId(Integer userOrganizationId) {
        this.userOrganizationId = userOrganizationId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }
}