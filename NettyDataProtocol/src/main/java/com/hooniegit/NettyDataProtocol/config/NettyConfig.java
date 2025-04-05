package com.hooniegit.NettyDataProtocol.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import jakarta.annotation.PreDestroy;

@Configuration
public class NettyConfig {

    @Bean
    public EventLoopGroup eventLoopGroup() {
        return new NioEventLoopGroup();
    }

    @PreDestroy
    public void shutdown(EventLoopGroup group) {
        group.shutdownGracefully();
    }
}

