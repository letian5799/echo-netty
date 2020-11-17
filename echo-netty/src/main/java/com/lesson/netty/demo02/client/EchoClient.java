package com.lesson.netty.demo02.client;

import com.lesson.netty.demo02.client.main.NettyEchoClientMain;

public class EchoClient {
    public static void main(String[] args) {
        try {
            new NettyEchoClientMain().run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
