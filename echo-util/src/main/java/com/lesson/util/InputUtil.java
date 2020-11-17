package com.lesson.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 数据输入工具类
 */
public class InputUtil {
    // 控制台输入，不采用Scanner的原因是不好控制输入的字符
    private static final BufferedReader KEYBOARD_INPUT = new BufferedReader(new InputStreamReader(System.in));

    private InputUtil(){}

    /**
     * 实现键盘输入擦欧总，可以返回String类型的数据
     * @param prompt
     * @return
     */
    public static String getStirng(String prompt){
        boolean flag = true;    //数据接受标记
        String str = null;
        while (flag){
            System.out.println(prompt);
            try {
                // 读取一行数据
                str = KEYBOARD_INPUT.readLine();
                if(str == null || "".equals(str)){
                    System.out.println("数据输入错误或者输入内容为空：");
                }else{
                    flag = false;
                }
            } catch (IOException e) {
                System.out.println("数据输入错误或者输入内容为空：");
            }
        }
        return str;
    }
}
