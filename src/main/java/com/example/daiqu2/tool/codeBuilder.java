package com.example.daiqu2.tool;

import java.util.Random;

public class codeBuilder {
    public static String getTaskCode(int size){
        String s="";
        Random random = new Random();
        for(int i=size;i>0;i--){
            int num = random.nextInt(25)+65;
            s+=(char)num;
        }
        return s;
    }
    public static void main(String[] args){

      try{
          System.out.print("code:"+getTaskCode(6));
      } catch (Exception e){
          e.printStackTrace();
      }
    }

}