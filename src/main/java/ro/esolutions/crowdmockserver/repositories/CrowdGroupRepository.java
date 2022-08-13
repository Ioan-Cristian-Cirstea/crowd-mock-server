package ro.esolutions.crowdmockserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.esolutions.crowdmockserver.entities.CrowdGroup;

import java.util.List;

@Repository
public interface CrowdGroupRepository extends JpaRepository<CrowdGroup, String> {
}
