package com.example.daiqu2.controller;

import com.example.daiqu2.data.mibaoData;
import com.example.daiqu2.data.userData;
import com.example.daiqu2.entity.mibaoTable;
import com.example.daiqu2.entity.userTable;
import com.example.daiqu2.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    userService uService;
    //注册新用户
    @RequestMapping("/login")
    @ResponseBody
    public String insetUserData(@RequestBody userData uData) {
        return uService.insertUser(uData);
    }
    //用户登录
    @RequestMapping("/load")
    @ResponseBody
    public String load(@RequestBody userData uData) {
        return uService.loadUser(uData);
    }

    @RequestMapping("/mibaoExit")
    @ResponseBody
    public List<String> mibao(@RequestBody mibaoData mbData) {
        return uService.mibaoExitList(mbData);
    }

    @RequestMapping("/insertMibao")
    @ResponseBody
    public String insetMibaoData(@RequestBody mibaoData mbData) {
        return uService.insertMibao(mbData);
    }

    //有密保的更新密码
    @RequestMapping("/updateForgetPwd")
    @ResponseBody
    public String updateForgetPwd(@RequestBody mibaoData mbData) {
        return uService.updateForgetPwd(mbData);
    }

    //没有密保的更新密码
    @RequestMapping("/updateForgetPwd2")
    @ResponseBody
    public String updateForgetPwd(@RequestBody userData uData) {
        return uService.updateForgetPwd(uData);
    }

    //查询用户信息
    @RequestMapping("/findUserInformation")
    @ResponseBody
    public userTable findUserByPhone(@RequestBody userData uData) {
        System.out.println(uData.getPhone());
        System.out.println(uService.findUserByPhone(uData).toString());
        return uService.findUserByPhone(uData);
    }

    //更新用户信息
    @RequestMapping("/updateUserInformation")
    @ResponseBody
    public String updateUserInformation(@RequestBody userData uData) {
        return uService.updateUser(uData);
    }

    //查询并显示密保信息
    @RequestMapping("/findMibao")
    @ResponseBody
    public mibaoTable findMibao(mibaoData mbData) {
        return uService.findMibao(mbData);
    }
}
