package ro.esolutions.crowdmockserver.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import ro.esolutions.crowdmockserver.entities.ApplicationCrowdGroupKey;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class ApplicationCrowdGroup {
    @EmbeddedId
    private ApplicationCrowdGroupKey applicationCrowdGroupKey;

    public ApplicationCrowdGroup(ApplicationCrowdGroupKey applicationCrowdGroupKey) {
        this.applicationCrowdGroupKey = applicationCrowdGroupKey;
    }
}
