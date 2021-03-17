package com.example.daiqu2.tool;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class postPicture {
    public String file_name;
    public static String time;
    public  boolean upLoad(MultipartFile file){
        String path = "E:/Program Files (x86)/taskPictureData";
        File filePath = new File(path);
        System.out.println("文件的保存路径：" + path);
        if (!filePath.exists() && !filePath.isDirectory()) {
            System.out.println("目录不存在，创建目录:" + filePath);
            filePath.mkdir();
        }
        String originalFileName = file.getOriginalFilename();
        originalFileName=originalFileName.replaceAll(",|&|=", "");
        System.out.println("原始文件名称：" + originalFileName);
        //获取文件类型，以最后一个`.`为标识
        String type = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        System.out.println("文件类型：" + type);
        //获取文件名称（不包含格式）
        String name = originalFileName.substring(0, originalFileName.lastIndexOf("."));
        //设置文件新名称: 当前时间+文件名称（不包含格式）
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");

        String date = sdf.format(d);
        //String fileName ="a"+date + name + "." + type;
        String fileName =date + "." + type;
        file_name = fileName;
        System.out.println("新文件名称：" + fileName);
        //在指定路径下创建一个文件
        File targetFile = new File(path, fileName);
        //将文件保存到服务器指定位置
        try {
            file.transferTo(targetFile);
            System.out.println("上传成功");
        } catch (IOException e) {
            System.out.println("上传失败");
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
