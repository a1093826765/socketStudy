package com.socket.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by IntelliJ IDEA.
 *
 * @Description:
 * @Author: november
 * @CreateTime: 2022/11/17 5:18 下午
 * @UpdateTIme:
 */
public class EchoClient1 {

    public static void main(String[] args) {
        int port = 7778;
        if (args.length != 0) {
            port = Integer.parseInt(args[0]);
        }
        Bootstrap b = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            b.group(group)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new EchoClientHandler1());

            b.bind(port).sync().channel().closeFuture().await();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public void test() throws Exception {
        int port = 5000; // 设置UDP接收端口号
        byte[] buffer = new byte[1024]; // 设置缓冲区大小
        DatagramSocket socket = new DatagramSocket(port); // 创建DatagramSocket对象
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length); // 创建DatagramPacket对象
        while (true) {
            socket.receive(packet); // 接收数据包
            String sdp = new String(packet.getData(), 0, packet.getLength()); // 将数据包转换为SDP格式字符串
            System.out.println("Received SDP: " + sdp); // 打印接收到的SDP数据
        }
    }
}