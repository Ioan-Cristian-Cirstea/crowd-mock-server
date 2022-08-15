package ro.esolutions.crowdmockserver.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
public class ApplicationCrowdGroupKey implements Serializable {
    @OneToOne
    @JoinColumn(name = "applicationUUID", referencedColumnName = "UUID")
    private Application application;
    @OneToOne
    @JoinColumn(name = "groupUUID", referencedColumnName = "UUID")
    private CrowdGroup crowdGroup;

    public ApplicationCrowdGroupKey(Application application, CrowdGroup crowdGroup) {
        this.application = application;
        this.crowdGroup = crowdGroup;
    }
}
