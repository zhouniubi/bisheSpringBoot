package com.example.daiqu2.controller;

import com.example.daiqu2.data.mibaoData;
import com.example.daiqu2.data.userData;
import com.example.daiqu2.entity.userTable;
import com.example.daiqu2.service.userService;
import com.example.daiqu2.tool.AES;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    userService uService ;
    @RequestMapping("/insert")
    @ResponseBody
    public String insetUserData(){
        String phone = "13955005387",introduce = "",name = "周周",pwd = "123456";
        int identity = 11, sex = 0;
        userData uData = new userData();
        uData.setPhone(AES.encrypt(phone));
        uData.setIdentity(identity);
        uData.setIntroduce(AES.encrypt(introduce));
        uData.setName(AES.encrypt(name));
        uData.setPwd(AES.encrypt(pwd));
        uData.setSex(sex);
        if(uService.insertUser(uData)==1) {
            return "插入成功";
        }
        else if(uService.insertUser(uData)==0){
            return "账户已经存在";
        }
            return "未知错误";
    }
    @RequestMapping("/delete")
    @ResponseBody
    public String deleteUserData(){
        String phone = "1234";
        userData uData = new userData();
        uData.setPhone(phone);
        if(uService.deleteUser(uData)){
            return "删除成功";
        }else
            return "删除失败";
    }
    @RequestMapping("/login")
    @ResponseBody
    public String login(@RequestBody userData uData){
        switch(uService.loginUser(uData)){
            case "111":
                return "111";//"登录成功"
            case "110":
                return "110";//"密码错误"
            case "000":
                return "000";//"用户不存在";
            default:
                return "其他错误";
        }
    }
    @RequestMapping("/view")
    @ResponseBody
    public String view(){
        String phone = "1234";
        String pwd = "123456";
        userData uData = new userData();
        uData.setPhone(phone);
        uData.setPwd(pwd);
        userTable uTable = uService.viewData(uData);
        return AES.decrypt(uTable.getName());
    }
    @RequestMapping("/mibaoExit")
    @ResponseBody
    public List<String> mibao(@RequestBody mibaoData mbData){
        List<String> list = new ArrayList<>();
        switch (uService.mibaoExit(mbData)){
            case "000" :
                System.out.println("000");
                list.add("000");
                list.add("##");
                return list;//用户不存在
            case "200" :
                System.out.println("200");
                list.add("200");
                list.add("#");
                return list;//密保不存在
            case "220" :
                System.out.println("220");
                list.add("220");
                list.add(uService.mibaoQuestion(mbData));
                return list;//密保2不存在
            case "202" :
                System.out.println("202");
                list.add("202");
                list.add(uService.mibaoQuestion(mbData));
                return list;//密保1不存在
            default:
                System.out.println("222");
                list.add("222");
                list.add(uService.mibaoQuestion(mbData));
                return list;//密保存在
        }
    }
    @RequestMapping("/insertMibao")
    @ResponseBody//@RequestBody mibaoData mbData
    public String insetMibaoData(){
        String phone = "13955005387",mibao1="崩三我最喜欢的角色",mibao2="我大学的宿舍是多少"
                ,answer1="德莉莎",answer2="617";
        mibaoData mb = new mibaoData();
        mb.setPhone(AES.encrypt(phone));
        //mb.setMibao1(AES.encrypt(mibao1));
        mb.setMibao2(AES.encrypt(mibao2));
        //mb.setAnswer1(AES.encrypt(answer1));
        mb.setAnswer2(AES.encrypt(answer2));
        if(uService.insertMibao(mb).equals("1")) {
            return "1";
        }
        return "0";
    }
    @RequestMapping("/updateForgetPwd")
    @ResponseBody
    public String updateForgetPwd(@RequestBody mibaoData mbData){

        return uService.updateForgetPwd(mbData);
    }

}