package syslog.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import syslog.infrastructure.convert.SyslogStandard;
import syslog.infrastructure.model.Syslog;
import syslog.infrastructure.repos.SyslogRepository;
import syslog.infrastructure.sender.Sender;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SyslogClient {
    private final SyslogStandard syslogStandard;
    private final SyslogRepository syslogRepository;
    private final Sender sender;

    @Value("${app.date_path}")
    private String path;

    @Scheduled(fixedDelayString = "${app.delay}")
    public void toSyslogServer() {
        try {
            List<Syslog> syslog = getLogs();
            if (!syslog.isEmpty()) {
                Timestamp timestamp = syslog.get(syslog.size() - 1).getPublishedOn();
                Files.write(Path.of(path), timestamp.toString().getBytes());
                log.info("Write timestamp into {}", path);
                sendByStandard(syslog);
            }
        } catch (IOException exception) {
            log.error(exception.getMessage(), exception);
        }
    }

    private List<Syslog> getLogs() throws IOException {
        return Files.exists(Path.of(path))
                ? syslogRepository.findByPublishedOnGreaterThan(Timestamp.valueOf(Files.readString(Path.of(path))))
                : syslogRepository.findAll();
    }

    private void sendByStandard(List<Syslog> syslog) {
        for(Syslog log : syslog) {
            byte[] logByStandard = syslogStandard.standardLog(log);
            sender.sendContent(logByStandard);
        }
    }
}
