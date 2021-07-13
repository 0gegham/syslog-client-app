package syslog.infrastructure.sender;

public interface Sender {
    void sendContent(byte[] content);
}
