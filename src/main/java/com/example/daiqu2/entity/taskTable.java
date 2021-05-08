package com.example.daiqu2.entity;

import lombok.Data;

import javax.persistence.*;
@Entity
@Table(name = "task_table",uniqueConstraints = {@UniqueConstraint(columnNames = "task_code")})
@Data
public class taskTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "task_code",nullable = false)
    private String taskCode;
    @Column(name = "publisher_phone",nullable = false)
    private String publisherPhone;
    @Column(name = "accepter_phone")
    private String accepterPhone;
    @Column(name = "type",nullable = false)
    private String type;
    @Column(name = "title",nullable = false)
    private String title;
    @Column(name = "get_place")
    private String getPlace;
    @Column(name = "post_place")
    private String postPlace;
    @Column(name = "need_time")
    private String needTime;
    @Column(name = "money")
    private String money;
    @Column(name = "information")
    private String infomation;
    @Column(name = "state")
    private String state;
    @Column(name = "pic")
    private String pic;
    @Column(name = "fb_time")
    private String time;
    @Column(name = "js_time")
    private String time2;
}
