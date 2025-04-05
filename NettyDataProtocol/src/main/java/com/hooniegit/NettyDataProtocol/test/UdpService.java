package com.hooniegit.NettyDataProtocol.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.hooniegit.NettyDataProtocol.netty.UdpSender;

@Service
public class UdpService {

    private final UdpSender udpSender;

    public UdpService(UdpSender udpSender) {
        this.udpSender = udpSender;
    }

    public void processAndSend(List<SuperData> input, List<Integer> idList) {
        Set<Integer> idSet = new HashSet<>(idList);

        List<String> prop1List = new ArrayList<>();
        for (SuperData data : input) {
            if (idSet.contains(data.getId())) {
                prop1List.add(data.getProp1());
            }
        }

        try {
            udpSender.send(prop1List, "192.168.0.101", 5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
