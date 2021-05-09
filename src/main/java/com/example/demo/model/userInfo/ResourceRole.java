package com.example.demo.model.userInfo;

public class ResourceRole {

    private Integer resourceId;
    private Integer roleId;

    /*扩展*/
    private String resourceName;

    private String roleName;

    private String requestUrl;
    private String apis;

    public String getApis() {
        return apis;
    }

    public void setApis(String apis) {
        this.apis = apis;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
