package com.example.daiqu2.tool;

import java.io.IOException;

public class SendPic {
    int port=8181;
    String path = "E:/Program Files (x86)/taskPictureData/";
    /**
     * 发送文件的方法
     * 此处定义服务器端口为9090,ip地址为192.168.1.1
     * 设定被传输图片的路径为"images/icon.png"
     * images文件夹放在此工程的根目录下，我们就可以通过相对路径访问这个图片文件了
     */
    private void sendPic(String name) {
        try {
            // 创建服务器
            java.net.ServerSocket ss = new java.net.ServerSocket(port);
            // 等待客户机接入，此处会阻塞,直到有客户机接入服务器
            java.net.Socket client = ss.accept();
            //创建将要被发送的图片的文件输入流
            java.io.FileInputStream fin = new java.io.FileInputStream(path+name);
            //获得套接字的输出流，并包装成数据输出流
            java.io.DataOutputStream dou = new java.io.DataOutputStream(client
                    .getOutputStream());
            // 向输出流中写入文件数据长度
            dou.writeInt(fin.available());//注：此处未考虑图片太大超出int范围，以至于出现内存溢出的情况
            // 将实际的图片数据读取到byte[] data中
            byte[] data = new byte[fin.available()];
            fin.read(data);
            // 将图片数据写入到输出流中
            dou.write(data);
            dou.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
