package com.socket.udp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

import java.net.InetSocketAddress;

/**
 * Created by IntelliJ IDEA.
 *
 * @Description:
 * @Author: november
 * @CreateTime: 2022/11/17 5:10 下午
 * @UpdateTIme:
 */
public class EchoServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    boolean flag = false;
    InetSocketAddress address1 = null;
    InetSocketAddress address2 = null;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket) throws Exception {
        System.out.println("channelRead0");
        System.out.println(address1+"---"+address2);
        ByteBuf byteBuf = (ByteBuf) datagramPacket.copy().content();
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        String str = new String(bytes, "utf-8");
        System.out.println(str);
        if (str.equalsIgnoreCase("L")) {
            //保存到addr1中 并发送addr2
            address1 = datagramPacket.sender();
            System.out.println("L 命令， 保存到addr1中 ");
        } else if (str.equalsIgnoreCase("R")) {
            //保存到addr2中 并发送addr1
            address2 = datagramPacket.sender();
            System.out.println("R 命令， 保存到addr2中 ");
        } else if (str.equalsIgnoreCase("M")) {
            //addr1 -> addr2
            String remot = "A " + address2.getAddress().toString().replace("/", "")
                    + " " + address2.getPort();
            channelHandlerContext.writeAndFlush(new DatagramPacket(
                    Unpooled.copiedBuffer(remot.getBytes()), address2));
            //addr2 -> addr1
            remot = "A " + address1.getAddress().toString().replace("/", "")
                    + " " + address1.getPort();
            channelHandlerContext.writeAndFlush(new DatagramPacket(
                    Unpooled.copiedBuffer(remot.getBytes()), address2));
            System.out.println("M 命令");
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive");
        System.out.println("服务器启动...");
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead");
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelWritabilityChanged");
        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelReadComplete");
        super.channelReadComplete(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("userEventTriggered");
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive");
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exceptionCaught");
        super.exceptionCaught(ctx, cause);
    }
}
