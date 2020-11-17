package com.lesson.netty.demo01;

import com.lesson.info.HostInfo;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 丢弃服务
 */
public class DiscardServer {
    public void run() throws Exception {
        // 创建主线程
        EventLoopGroup bossGroup = new NioEventLoopGroup(5);
        // 创建工作线程
        EventLoopGroup workGroup = new NioEventLoopGroup(10);
        try {
            // 启动 NIO 服务的辅助启动类
            ServerBootstrap sb = new ServerBootstrap();
            sb.group(bossGroup, workGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel channel) throws Exception {
                    // 添加处理器
                    channel.pipeline().addLast(new DiscardServerHandler());
                }
                // option() 是提供给NioServerSocketChannel 用来接收进来的连接
            // childOption() 是提供给由父管道 ServerChannel 接收到的连接
            }).option(ChannelOption.SO_BACKLOG,100).childOption(ChannelOption.SO_KEEPALIVE, true);
            // 绑定端口，等待连接
            ChannelFuture future = sb.bind(HostInfo.HOST_NAME, HostInfo.PORT).sync();
            // 等待服务器关闭
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        try {
            new DiscardServer().run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
