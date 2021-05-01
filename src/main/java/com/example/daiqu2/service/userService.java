package com.example.daiqu2.service;

import com.example.daiqu2.data.mibaoData;
import com.example.daiqu2.data.userData;
import com.example.daiqu2.entity.userTable;

public interface userService {
    int insertUser(userData uData);

    boolean userExit(userData uData);

    boolean deleteUser(userData uData);

    String loginUser(userData uData);

    userTable viewData(userData uData);

    String mibaoExit(mibaoData mibao);

    String insertMibao(mibaoData mibao);

    String mibaoQuestion(mibaoData mibao);

    String updateForgetPwd(mibaoData mbData);

    String updateForgetPwd(userData uData);

    userTable findUserByPhone(userData uData);

    String updateUser(userData uData);
}
