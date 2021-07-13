package syslog.infrastructure.convert;

import syslog.infrastructure.model.Syslog;


public interface SyslogStandard {
    byte[] standardLog(Syslog syslog);
}
