package com.socket.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 *
 * @Description:
 * @Author: november
 * @CreateTime: 2022/11/17 5:18 下午
 * @UpdateTIme:
 */
public class EchoClientHandler1 extends SimpleChannelInboundHandler<DatagramPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket) throws Exception {
        System.out.println("channelRead0");
        //服务器推送对方IP和PORT
        ByteBuf buf = (ByteBuf) datagramPacket.copy().content();
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String str = new String(req, "UTF-8");
        System.out.println("接收到的信息:" + str);
//        String VIDEO_FILE_PATH = "/Users/november/Downloads/test.h264";
//        String VIDEO_FILE_PATH = "/Users/november/Desktop/file/realpaly.h264";
        String VIDEO_FILE_PATH = "/Users/november/Desktop/file/realpaly(6).h264";
        RandomAccessFile videoFile = new RandomAccessFile(VIDEO_FILE_PATH, "r");
        byte[] bytes = new byte[1024];
        int i = 0;
        System.out.println(123);
        while (videoFile.read(bytes) != -1) {
            channelHandlerContext.writeAndFlush(new DatagramPacket(
                    Unpooled.copiedBuffer(bytes),
                    new InetSocketAddress("192.168.0.78", 7402)));
            System.out.println(i);
            Thread.sleep(10);
            i++;
        }
//        channelHandlerContext.writeAndFlush(new DatagramPacket(
//                Unpooled.copiedBuffer("视频数据".getBytes()),
//                new InetSocketAddress(a[0], Integer.parseInt(a[1]))));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive");
        System.out.println("客户端向服务器发送自己的IP和PORT");
//        ctx.writeAndFlush(new DatagramPacket(
//                Unpooled.copiedBuffer("L".getBytes()),
//                new InetSocketAddress("192.168.0.78", 7402)));
//        ctx.writeAndFlush(new DatagramPacket(
//                Unpooled.copiedBuffer("M".getBytes()),
//                new InetSocketAddress("192.168.0.78", 7402)));
        ctx.writeAndFlush(new DatagramPacket(
                Unpooled.copiedBuffer(hexToBytes("FFFF0001000100010001642BE42A003C6434636465336635373033316632333130303030000000000005000000020001000100010001000100020002000200020002000300030003000300038465FF")),
                new InetSocketAddress("192.168.0.78", 7402)));
//        ctx.writeAndFlush(new DatagramPacket(
//                Unpooled.copiedBuffer("test 123456 视频数据".getBytes()),
//                new InetSocketAddress("8.134.71.158", 7402)));
//        super.channelActive(ctx);
    }

    /**
     * Created by IntelliJ IDEA.
     *
     * @param src String Byte字符串，每个Byte之间没有分隔符(字符范围:0-9 A-F)
     * @return
     * @Description: 十六进制转byte
     * bytes字符串转换为Byte值
     * @author november
     * @CreateTime: 2021/9/15 2:45 下午
     * @UpdateTime:
     */
    public static byte[] hexToBytes(String src) {
        if (src.length() % 2 == 1) {
            src = "0" + src;
        }
        /*对输入值进行规范化整理*/
        src = src.trim().replace(" ", "").toUpperCase(Locale.US);
        //处理值初始化
        int m = 0, n = 0;
        int iLen = src.length() / 2; //计算长度
        byte[] ret = new byte[iLen]; //分配存储空间

        for (int i = 0; i < iLen; i++) {
            m = i * 2 + 1;
            n = m + 1;
            ret[i] = (byte) (Integer.decode("0x" + src.substring(i * 2, m) + src.substring(m, n)) & 0xFF);
        }
        return ret;
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
