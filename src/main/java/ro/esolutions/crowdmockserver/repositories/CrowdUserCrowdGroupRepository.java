package ro.esolutions.crowdmockserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.esolutions.crowdmockserver.entities.CrowdUserCrowdGroup;

import java.util.List;

@Repository
public interface CrowdUserCrowdGroupRepository extends JpaRepository<CrowdUserCrowdGroup, String> {
    public CrowdUserCrowdGroup findByCrowdUser_UUIDAndCrowdGroup_UUID(String userUUID, String groupUUID);
}

