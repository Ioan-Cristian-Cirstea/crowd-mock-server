package ro.esolutions.crowdmockserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.esolutions.crowdmockserver.entities.ApplicationCrowdGroup;

import java.util.List;

@Repository
public interface ApplicationCrowdGroupRepository extends JpaRepository<ApplicationCrowdGroup, String> {
    public List<ApplicationCrowdGroup> findAllByApplicationUUID(String applicationUUID);
    public void deleteAllByCrowdGroup_UUID(String groupUUID);
}
