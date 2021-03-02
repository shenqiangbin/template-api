package com.example.demo.repository;

import com.example.demo.base.SQLHelper;
import com.example.demo.model.userInfo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    private SQLHelper sqlHelper;

    public User getUserByUserCode(String userCode) throws Exception {

        String sql = "select * from user where usercode = ? and status = 1";
        List<Object> params = new ArrayList<>();
        params.add(userCode);

        List<User> users = sqlHelper.query(sql, params, User.class);
        if (users != null && users.size() > 0)
            return users.get(0);
        else if (users.size() > 1)
            throw new Exception("usercode重复:" + userCode);

        return null;
    }

    public boolean ChangePwd(String loginUser, String newPwd) {
        List<Object> params = new ArrayList<>();
        params.add(newPwd);
        params.add(loginUser);
        params.add(loginUser);
        int rtn = sqlHelper.update("update user set password = ?,modifyuser = ?,modifytime=now() where usercode = ?", params);
        return rtn > 0;
    }
}
