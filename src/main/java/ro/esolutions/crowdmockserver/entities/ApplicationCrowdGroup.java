package ro.esolutions.crowdmockserver.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import ro.esolutions.crowdmockserver.entities.ApplicationCrowdGroupKey;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@IdClass(ApplicationCrowdGroupKey.class)
public class ApplicationCrowdGroup {
    @Id
    @OneToOne
    @JoinColumn(name = "applicationUUID", referencedColumnName = "UUID")
    private Application application;
    @Id
    @OneToOne
    @JoinColumn(name = "groupUUID", referencedColumnName = "UUID")
    private CrowdGroup crowdGroup;
}
