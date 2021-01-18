package com.example.daiqu2.service;

import com.example.daiqu2.data.mibaoData;
import com.example.daiqu2.data.userData;
import com.example.daiqu2.entity.userTable;

public interface userService {
    int insertUser(userData user);
    boolean userExit(userData user);
    boolean deleteUser(userData user);
    String loginUser(userData user);
    userTable viewData(userData user);
    String mibaoExit(mibaoData mibao);
    String insertMibao(mibaoData mibao);
    String mibaoQuestion(mibaoData mibao);
    String updateForgetPwd(mibaoData mbData);
}
