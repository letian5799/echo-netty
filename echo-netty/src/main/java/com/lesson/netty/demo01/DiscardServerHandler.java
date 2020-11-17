package com.lesson.netty.demo01;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

public class DiscardServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        // 将netty的缓冲数据类型转换成string类型
        String readMsg = byteBuf.toString(CharsetUtil.UTF_8);
        System.out.print(readMsg);
        ctx.writeAndFlush(readMsg);
        // 直接丢弃数据
        // ((ByteBuf)msg).release();
    }

    /**
     * IO错误处理或者异常事件处理
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
