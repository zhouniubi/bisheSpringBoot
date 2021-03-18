package com.example.daiqu2.data;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class taskDataWithName {
    private String publisherName;
    private String accepterName;
    private Integer id;
    private String taskCode;
    private String publisherPhone;
    private String accepterPhone;
    private String type;
    private String title;
    private String getPlace;
    private String postPlace;
    private String needTime;
    private String money;
    private String infomation;
    private String state;
    private String pic;
    private MultipartFile file;
    private String time;
    private String time2;
}
