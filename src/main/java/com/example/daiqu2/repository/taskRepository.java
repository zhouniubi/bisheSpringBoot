package com.example.daiqu2.repository;

import com.example.daiqu2.entity.taskTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface taskRepository extends JpaRepository<taskTable,Integer> {
    //通过任务码寻找任务
    taskTable findByTaskCode(String code);
    //通过发布者手机号查询发布的任务
    List<taskTable> findAllByPublisherPhone(String phone);
    //通过任务码删除相关的课程
    void deleteByTaskCode(String code);
    //查询所有未接单的任务
    List<taskTable> findAllByState(String state);
    //根据任务码更新任务状态
    @Transactional
    @Modifying
    @Query(value="update task_table tk set tk.state = ?2 where tk.task_code= ?1",nativeQuery = true)
    void updateStateByCode(String code,String state);
    //根据任务码更新任务状态
    @Transactional
    @Modifying
    @Query(value="update task_table tk set tk.state = ?2 , " +
            "tk.js_time = ?3 , tk.accepter_phone = ?4 where tk.task_code= ?1",nativeQuery = true)
    void updateStateAndNameByCode(String code,String state,String date,String phone);

}
