package com.hooniegit.NettyDataProtocol.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;
import org.springframework.stereotype.Component;

@Component
public class UdpHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) {
        ByteBuf content = packet.content();
        String received = content.toString(CharsetUtil.UTF_8);
        System.out.println("📥 Received UDP message: " + received);

        // 예시: 송신자 주소
        String sender = packet.sender().toString();
        System.out.println("📨 From: " + sender);

        // TODO: 수신 데이터 처리 로직 (DB 저장, 응답 송신 등)
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.err.println("❌ Error in UdpHandler: " + cause.getMessage());
        cause.printStackTrace();
    }
}

