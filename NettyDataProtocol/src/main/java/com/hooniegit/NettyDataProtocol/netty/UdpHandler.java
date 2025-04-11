package com.hooniegit.NettyDataProtocol.netty;

import com.hooniegit.Xerializer.Serializer.KryoSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.ReferenceCountUtil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UdpHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) {
        ByteBuf content = packet.content();
        byte[] data = new byte[content.readableBytes()];
        content.readBytes(data); // 바이트 배열로 읽기

        try {
            // Kryo 역직렬화
            @SuppressWarnings("unchecked")
            List<Object> receivedList = KryoSerializer.deserialize(data);

            System.out.println("📥 Received UDP object list:");
            for (Object obj : receivedList) {
                System.out.println("  ↪ " + obj);
            }

            String sender = packet.sender().toString();
            System.out.println("📨 From: " + sender);

            // TODO: 수신 객체 처리 로직 (e.g. DB 저장, 이벤트 디스패치 등)

        } catch (Exception e) {
            System.err.println("❌ Failed to deserialize UDP data: " + e.getMessage());
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(content); // 명시적 release (Netty best practice)
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.err.println("❌ Error in UdpHandler: " + cause.getMessage());
        cause.printStackTrace();
    }
}
