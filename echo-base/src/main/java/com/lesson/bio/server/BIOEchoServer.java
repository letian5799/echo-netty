package com.lesson.bio.server;

import com.lesson.info.HostInfo;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BIOEchoServer {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            // 启动一个socket服务
            serverSocket = new ServerSocket(HostInfo.PORT);
            System.out.println("服务端已经启动，监听端口为：" + HostInfo.PORT);
            // 循环处理标志
            boolean flag = true;
            // 定义一个固定线程池去执行任务
            ExecutorService executorService = Executors.newFixedThreadPool(10);
            while (flag){
                // 接收一个客户端，此处会阻塞
                Socket client = serverSocket.accept();
                // 处理客户端任务
                executorService.submit(new EchoClientHandler(client));
            }
            executorService.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 客户端处理的一个内部类
     */
    private static class EchoClientHandler implements Runnable{
        private Socket client;
        private Scanner scanner;
        private PrintStream out;
        private boolean flag = true;
        public EchoClientHandler(Socket client){
            this.client = client;
            try {
                // 获取客户端输入流
                this.scanner = new Scanner(this.client.getInputStream());
                // 设置换行符
                this.scanner.useDelimiter("\n");
                // 获取客户端输出流
                this.out = new PrintStream(this.client.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void run() {
            while (this.flag){
                if(scanner.hasNext()){
                    // 接收客户端输入内容
                    String val = this.scanner.next().trim();
                    System.out.println("server：" + val);
                    if("exit".equals(val)){
                        this.out.println("byebye...");
                        this.flag = false;
                    }else{
                        out.println("[echo] " + val );
                    }
                }
            }
            this.scanner.close();
            this.out.close();
            try {
                this.client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
