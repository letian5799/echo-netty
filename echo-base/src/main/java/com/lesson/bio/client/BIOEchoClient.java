package com.lesson.bio.client;

import com.lesson.info.HostInfo;
import com.lesson.util.InputUtil;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class BIOEchoClient {
    public static void main(String[] args) {
        Socket client;
        // 交互标记
        boolean flag = true;
        try {
            // 建立一个客户端连接
            client = new Socket(HostInfo.HOST_NAME,HostInfo.PORT);
            // 获取服务端的响应数据
            Scanner scanner = new Scanner(client.getInputStream());
            scanner.useDelimiter("\n");
            // 向服务端发送数据
            PrintStream out = new PrintStream(client.getOutputStream());
            while (flag){
                String inputData = InputUtil.getStirng("请输入要发送的内容：").trim();
                // 将数据发送到服务端
                out.println(inputData);
                // 获取服务端响应数据
                if(scanner.hasNext()){
                    String next = scanner.next().trim();
                    System.out.println("sssss" + next);
                    System.out.println(next);
                }
                if("exit".equalsIgnoreCase(inputData)){
                    flag = false;
                }
            }
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
