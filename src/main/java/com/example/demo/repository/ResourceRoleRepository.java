package com.example.demo.repository;

import com.example.demo.base.SQLHelper;
import com.example.demo.model.userInfo.ResourceRole;
import com.example.demo.model.userInfo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ResourceRoleRepository {

    @Autowired
    private SQLHelper sqlHelper;

    public List<ResourceRole> getAll() {
        String sql = "select r.id resourceId, r.name resourceName, r.requestUrl, r.apis, ro.id as roleid ,ro.rolename from resourceRole as rs \n" +
                "left JOIN resource r on rs.resourceId  = r.id\n" +
                "left JOIN Role ro on rs.roleId = ro.id\n" +
                "where rs.`status` = 1 and r.`status` = 1 and ro.`status` = 1";
        List<Object> params = new ArrayList<>();
        List<ResourceRole> models = sqlHelper.query(sql, params, ResourceRole.class);
        return models;
    }
}
