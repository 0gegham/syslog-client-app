package syslog.infrastructure.sender.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import syslog.infrastructure.sender.Sender;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

@Slf4j
@Component
public class UDPSender implements Sender {

    @Value("${server.host}")
    private String host;

    @Value("${server.port}")
    private int udpPort;

    @Override
    public void sendContent(byte[] content) {
        log.info("Send to UDP: {}", new String(content));
        InetSocketAddress inetSocketAddress = new InetSocketAddress(host, udpPort);
        DatagramPacket datagramPacket;
        try (DatagramSocket datagramSocket = new DatagramSocket()) {
            datagramPacket = new DatagramPacket(content, content.length, inetSocketAddress);
            datagramSocket.send(datagramPacket);
        } catch (IOException exception) {
            log.error(exception.getMessage(), exception);
        }
        log.info("Sent successfully: {}", new String(content));
    }
}
