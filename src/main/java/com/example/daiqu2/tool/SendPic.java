package com.example.daiqu2.tool;

import java.io.File;
import java.io.FileInputStream;

public class SendPic {
    static String path = "E:/Program Files (x86)/taskPictureData/";
    public static byte[] sendPic(String name) {
        String url = path + name;
        try {
            File file = new File(url);
            FileInputStream inputStream = new FileInputStream(file);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes,0,inputStream.available());
            return bytes;
        }catch (Exception e){
            e.printStackTrace();
            return new byte[1];
        }
    }

}
