package syslog.infrastructure.convert.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import syslog.infrastructure.convert.SyslogStandard;
import syslog.infrastructure.model.Syslog;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(
        value = "app.standard",
        havingValue = "RFC3164",
        matchIfMissing = true
)
public class RFC3164Standard implements SyslogStandard {

    @Override
    public byte[] standardLog(Syslog syslog) {
        String date = new SimpleDateFormat("MMM dd HH:mm:ss").format(syslog.getPublishedOn());
        String pri = "<" + (syslog.getFacility() * 8 + syslog.getSeverity()) + ">";
        return MessageFormat.format("{0}{1} {2} {3}: {4}",
                pri, date, syslog.getHostname(), syslog.getTag(), syslog.getContent()).getBytes();
    }
}
