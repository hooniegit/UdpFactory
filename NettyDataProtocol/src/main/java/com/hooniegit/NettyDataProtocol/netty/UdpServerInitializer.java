package com.hooniegit.NettyDataProtocol.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.ChannelPipeline;
import org.springframework.stereotype.Component;

@Component
public class UdpServerInitializer extends ChannelInitializer<DatagramChannel> {

    private final UdpHandler udpHandler;

    public UdpServerInitializer(UdpHandler udpHandler) {
        this.udpHandler = udpHandler;
    }

    @Override
    protected void initChannel(DatagramChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        // 필요한 경우 디코더 추가 가능 (UDP는 보통 Raw 처리)
        pipeline.addLast(udpHandler);
    }
}
