package ro.esolutions.crowdmockserver.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import ro.esolutions.crowdmockserver.entities.CrowdUser;
import ro.esolutions.crowdmockserver.entities.CrowdGroup;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class CrowdUserCrowdGroup {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String UUID;
    @OneToOne
    @JoinColumn(name = "userUUID")
    private CrowdUser crowdUser;
    @OneToOne
    @JoinColumn(name = "groupUUID")
    private CrowdGroup crowdGroup;

    public CrowdUserCrowdGroup(CrowdUser crowdUser, CrowdGroup crowdGroup) {
        this.crowdUser = crowdUser;
        this.crowdGroup = crowdGroup;
    } 
}
