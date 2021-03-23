package com.example.daiqu2.controller;

import com.example.daiqu2.data.taskData;
import com.example.daiqu2.data.taskDataWithName;
import com.example.daiqu2.entity.taskTable;
import com.example.daiqu2.service.taskService;
import com.example.daiqu2.tool.SendPic;
import com.example.daiqu2.tool.postPicture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class taskController {
    @Autowired
    taskService tkService;
    //测试图片上传功能
    @RequestMapping("/testUploadPic")
    @ResponseBody
    public String testUploadPic(@RequestBody MultipartFile file){
        System.out.print("test");
        postPicture post = new postPicture();
        if(post.upLoad(file)){
            return "1";
        }else{
            return "0";
        }
    }
    //创建任务
    @RequestMapping("/createTask")
    @ResponseBody
    public String createTask(MultipartFile file, taskData data){
        return tkService.createTask(data,file);
    }
    //查询发布的任务
    @RequestMapping("/findPublisherPhone")
    @ResponseBody
    public List<taskTable> findPublisherPhone(@RequestBody taskData data){
        return tkService.findTaskByPublisherPhone(data.getPublisherPhone());
    }
    //查询发布的任务伴随名字返回
    @RequestMapping("/findTaskWithName")
    @ResponseBody
    public List<taskDataWithName> findTaskWithName(@RequestBody taskData data){
        return tkService.findTaskWithNameByPublisherPhone(data.getPublisherPhone());
    }
    //查询没有接单的任务伴随名字返回
    @RequestMapping("/findTaskByState")
    @ResponseBody
    public List<taskDataWithName> findTaskByState(@RequestBody taskData data){
        return tkService.findTaskByState(data);
    }
    //加载任务图片
    @RequestMapping("/loadPic")
    @ResponseBody
    public byte[] loadPic(MultipartFile file, taskData data){
        byte[] dataByte = SendPic.sendPic(data.getPic());
        /*System.out.println("pic是："+data.getPic());
        System.out.println("dataByte是："+dataByte);*/
        return dataByte;
    }
    //更新任务信息数据
    @RequestMapping("/updateTask")
    @ResponseBody
    public String updateTask(MultipartFile file, taskData data){
        String state = tkService.updateTaskByCode(data,file);
        return state;
    }
    //删除指定任务码的任务
    @RequestMapping("/deleteTask")
    @ResponseBody
    public String deleteTask(@RequestBody taskData data){
        return tkService.deleteTaskByCode(data);
    }
    //更新任务的状态
    @RequestMapping("/updateState1")
    @ResponseBody
    public String updateState(@RequestBody taskData data){
        return tkService.updateStateByCode(data);
    }
}
