package com.example.daiqu2.entity;
import lombok.Data;

import javax.persistence.*;
@Entity
@Table(name = "user_table",uniqueConstraints = {@UniqueConstraint(columnNames = "phone")})
@Data
public class userTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "phone",nullable = false)
    private String phone;
    @Column(name = "pwd",nullable = false)
    private String pwd;
    @Column(name = "sex",nullable = false)
    private String sex;
    @Column(name = "identity",nullable = false)
    private String identity;
    @Column(name = "introduce")
    private String introduce;



}
