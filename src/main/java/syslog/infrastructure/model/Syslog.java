package syslog.infrastructure.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity(name = "syslog")
@NoArgsConstructor
@AllArgsConstructor
public class Syslog {
    @Id
    private UUID id;
    @Column(name = "published_on")
    private Timestamp publishedOn;
    private Integer facility;
    private Integer severity;
    private Integer version;
    private String hostname;
    private String tag;
    private String content;
    @Column(name = "msg_id")
    private String msgIdl;
}
