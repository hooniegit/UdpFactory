package com.hooniegit.NettyDataProtocol.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import com.hooniegit.NettyDataProtocol.netty.UdpSender;

@Service
public class UdpService {

    private final UdpSender udpSender;

    public UdpService(UdpSender udpSender) {
        this.udpSender = udpSender;
    }

    @PostConstruct
    public void processAndSend() {
        List<SuperData> prop1List = new ArrayList<>();

        for (int i = 0; i < 2000; i++) {
            SuperData data = new SuperData(i, "prop1_" + i);
            prop1List.add(data);
        }

        try {
            udpSender.send(prop1List, "localhost", 5000);
            System.out.println("Data sent successfully: " + prop1List.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
