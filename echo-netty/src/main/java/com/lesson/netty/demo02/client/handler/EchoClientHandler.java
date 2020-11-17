package com.lesson.netty.demo02.client.handler;

import com.lesson.util.InputUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

public class EchoClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            ByteBuf readBuf = (ByteBuf)msg;
            String readData = readBuf.toString(CharsetUtil.UTF_8);
            if("quit".equalsIgnoreCase(readData)){
                System.out.println("bye bye...");
                ctx.close();
            }else{
                System.out.println(readData);
                String s = InputUtil.getStirng("请输入消息：");
                byte[] sendMsg = s.getBytes();
                ByteBuf buf = Unpooled.buffer(sendMsg.length);
                buf.writeBytes(sendMsg);
                ctx.writeAndFlush(buf);
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
