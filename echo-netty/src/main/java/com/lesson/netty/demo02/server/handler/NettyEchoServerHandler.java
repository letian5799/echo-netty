package com.lesson.netty.demo02.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.ReferenceCounted;

public class NettyEchoServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        byte [] data = "服务已激活".getBytes();
        ByteBuf buf = Unpooled.buffer(data.length);
        buf.writeBytes(data);
        // 强制性发送所有数据
        ctx.writeAndFlush(buf);
    }

    /**
     * 数据信息的读取操作，也可以直接回应客户端消息
     * @param ctx 处理器上下文
     * @param msg 接收到的消息
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            ByteBuf buf = (ByteBuf) msg;
            String inputData = buf.toString(CharsetUtil.UTF_8).trim();
            System.out.println("接收到的客户端消息："+inputData);
            // 回应数据处理
            String respMsg = "[echo] "+ inputData;
            if("exit".equalsIgnoreCase(inputData)){
                respMsg = "quit";
            }
            byte[] data = respMsg.getBytes();
            ByteBuf respBuf = Unpooled.buffer(data.length);
            respBuf.writeBytes(data);
            ctx.writeAndFlush(respBuf);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            // 释放缓存
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable throwable) throws Exception {
        throwable.printStackTrace();
        ctx.close();
    }
}
