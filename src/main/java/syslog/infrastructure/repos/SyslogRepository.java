package syslog.infrastructure.repos;

import org.springframework.data.repository.RepositoryDefinition;
import syslog.infrastructure.model.Syslog;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@RepositoryDefinition(domainClass = Syslog.class, idClass = UUID.class)
public interface SyslogRepository {
    List<Syslog> findAll();
    List<Syslog> findByPublishedOnGreaterThan(Timestamp timestamp);
}
