package com.example.daiqu2.repository;

import com.example.daiqu2.entity.taskTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface taskRepository extends JpaRepository<taskTable,Integer> {
    //通过任务码寻找任务
    taskTable findByTaskCode(String code);
    //通过发布者手机号查询发布的任务
    List<taskTable> findAllByPublisherPhone(String phone);
}
