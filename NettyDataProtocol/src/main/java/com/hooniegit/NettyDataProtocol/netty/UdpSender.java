package com.hooniegit.NettyDataProtocol.netty;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.hooniegit.NettyDataProtocol.test.SuperData;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import jakarta.annotation.PreDestroy;

import com.hooniegit.Xerializer.Serializer.KryoSerializer;

@Component
public class UdpSender {

    private final EventLoopGroup group;
    private Channel channel;

    public UdpSender(EventLoopGroup group) {
        this.group = group;
        initChannel(); // 생성자에서 채널 생성
    }

    private void initChannel() {
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                     .channel(NioDatagramChannel.class)
                     .option(ChannelOption.SO_BROADCAST, true)
                     .handler(new ChannelInitializer<NioDatagramChannel>() {
                         @Override
                         protected void initChannel(NioDatagramChannel ch) {
                             // No handler needed for outbound-only
                         }
                     });

            this.channel = bootstrap.bind(0).sync().channel(); // ephemeral port
        } catch (Exception e) {
            throw new RuntimeException("UDP Channel init failed", e);
        }
    }

    public void send(List<SuperData> dataList, String host, int port) {
        try {
            byte[] serializedBytes = KryoSerializer.serialize(dataList); // Kryo 직렬화
            ByteBuf buf = Unpooled.wrappedBuffer(serializedBytes);

            DatagramPacket packet = new DatagramPacket(buf, new InetSocketAddress(host, port));
            channel.writeAndFlush(packet).addListener(future -> {
                if (!future.isSuccess()) {
                    System.err.println("❌ Failed to send UDP packet: " + future.cause());
                }
            });

        } catch (Exception e) {
            System.err.println("❌ Failed to serialize data for UDP: " + e.getMessage());
        }
    }

    @PreDestroy
    public void shutdown() {
        if (channel != null && channel.isOpen()) {
            channel.close();
        }
    }
}

