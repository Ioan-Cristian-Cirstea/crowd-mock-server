package ro.esolutions.crowdmockserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.esolutions.crowdmockserver.entities.ApplicationCrowdGroup;
import ro.esolutions.crowdmockserver.entities.ApplicationCrowdGroupKey;

import java.util.List;

@Repository
public interface ApplicationCrowdGroupRepository extends JpaRepository<ApplicationCrowdGroup,
       ApplicationCrowdGroupKey> {
    List<ApplicationCrowdGroup> findAllByApplicationCrowdGroupKey_Application_UUID(String applicationUUID);
}
