package com.lesson.netty.demo02.server;

import com.lesson.netty.demo02.server.main.NettyEchoServer;

public class EchoServer {
    public static void main(String[] args) {
        try {
            new NettyEchoServer().run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
