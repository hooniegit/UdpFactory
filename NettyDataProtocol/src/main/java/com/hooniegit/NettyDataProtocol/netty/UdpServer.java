package com.hooniegit.NettyDataProtocol.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import jakarta.annotation.PreDestroy;

import org.springframework.stereotype.Component;

@Component
public class UdpServer {

    private final UdpServerInitializer initializer;
    private final NioEventLoopGroup group = new NioEventLoopGroup();
    private Channel channel;

    public UdpServer(UdpServerInitializer initializer) {
        this.initializer = initializer;
    }

    @jakarta.annotation.PostConstruct
    public void start() throws Exception {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(initializer);

        channel = bootstrap.bind(5000).sync().channel();  // 수신 포트
        System.out.println("✅ UDP Server started on port 5000");
    }

}

