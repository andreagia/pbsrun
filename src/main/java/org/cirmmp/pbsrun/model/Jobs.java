package org.cirmmp.pbsrun.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
@Setter
@Getter
public class Jobs {
    private @Id
    @GeneratedValue
    Long id;
    private String directory;
    private String jobid;
    private Date dateTime;

    public Jobs() {}

    public Jobs(String directory, String jobid) {
        this.directory = directory;
        this.jobid = jobid;
        this.dateTime = new Date();
    }
}
