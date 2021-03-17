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
}
