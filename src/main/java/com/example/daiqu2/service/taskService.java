package com.example.daiqu2.service;

import com.example.daiqu2.data.taskData;
import com.example.daiqu2.data.taskDataWithName;
import com.example.daiqu2.entity.taskTable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface taskService {
    String createTask(taskData data, MultipartFile file);
    List<taskTable> findTaskByPublisherPhone(String phone);
    List<taskDataWithName> findTaskWithNameByPublisherPhone(String phone);
    String findPicByCode(String code);
    //通过任务码更新任务
    String updateTaskByCode(taskData data,MultipartFile file);
    //通过任务码删除课程
    String deleteTaskByCode(taskData data);
    //通过任务码更新任务的状态
    String updateStateByCode(taskData data);
    //通过任务码更新任务的状态与任务的名称
    String updateStateAndNameByCode(taskData data);
    //通过状态查询任务
    List<taskDataWithName> findTaskByState(taskData data);
}
