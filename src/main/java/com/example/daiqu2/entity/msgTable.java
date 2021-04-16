package com.example.daiqu2.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "msg_table")
@Data
public class msgTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "msg")
    private String msg;
    @Column(name = "from_user")
    private String fromUser;
    @Column(name = "to_user")
    private String toUser;
    @Column(name = "time")
    private String time;
    @Column(name = "state")
    private String state;
    @Column(name = "pic")
    private String pic;

}
