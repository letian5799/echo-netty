package com.lesson.netty.demo02.client.main;

import com.lesson.info.HostInfo;
import com.lesson.netty.demo02.client.handler.EchoClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyEchoClientMain {
    public void run() throws Exception{
        EventLoopGroup group = new NioEventLoopGroup(10);
        try {
            Bootstrap sb = new Bootstrap();
            sb.group(group);
            sb.channel(NioSocketChannel.class);
            sb.option(ChannelOption.TCP_NODELAY, true);
            sb.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new EchoClientHandler());
                }
            });
            ChannelFuture f = sb.connect(HostInfo.HOST_NAME, HostInfo.PORT).sync();
            f.channel().closeFuture().sync();

        } finally {
            group.shutdownGracefully();
        }
    }
}
