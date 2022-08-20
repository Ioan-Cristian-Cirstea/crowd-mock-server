package ro.esolutions.crowdmockserver.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class ApplicationCrowdGroup {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String UUID;
    @OneToOne
    @JoinColumn(name = "applicationUUID")
    private Application application;
    @OneToOne
    @JoinColumn(name = "groupUUID")
    private CrowdGroup crowdGroup;

    public ApplicationCrowdGroup(Application application, CrowdGroup crowdGroup) {
        this.application = application;
        this.crowdGroup = crowdGroup;
    } 
}
