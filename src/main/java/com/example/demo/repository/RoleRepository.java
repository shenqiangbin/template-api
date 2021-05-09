package com.example.demo.repository;

import com.example.demo.base.SQLHelper;
import com.example.demo.model.userInfo.Role;
import com.example.demo.model.userInfo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RoleRepository {

    @Autowired
    private SQLHelper sqlHelper;

    public List<Role> getRolesByUserId(String userId) {
        String sql = "select * from role where status = 1 and id in (select roleId from userRole where userId = ? and status = 1)";
        List<Object> params = new ArrayList<>();
        params.add(userId);

        List<Role> models = sqlHelper.query(sql, params, Role.class);
        return models;
    }

    public List<Role> getRolesByUserCode(String userCode) {
        String sql =
                "SELECT role.* from role\n" +
                "left join userRole on userRole.roleId = role.id\n" +
                "left join `user` on userRole.userId = user.id\n" +
                "where role.`status` = 1 and userRole.`status` = 1 and user.`status` = 1\n" +
                "and `user`.userCode = ?";
        List<Object> params = new ArrayList<>();
        params.add(userCode);

        List<Role> models = sqlHelper.query(sql, params, Role.class);
        return models;
    }
}
