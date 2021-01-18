package com.example.daiqu2.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "mibao_table",uniqueConstraints = {@UniqueConstraint(columnNames = "phone")})
@Data
public class mibaoTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "phone",nullable = false)
    private String phone;
    @Column(name = "mibao1")
    private String mibao1;
    @Column(name = "answer1")
    private String answer1;
    @Column(name = "mibao2")
    private String mibao2;
    @Column(name = "answer2")
    private String answer2;
}
