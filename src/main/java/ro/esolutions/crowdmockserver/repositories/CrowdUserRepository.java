package ro.esolutions.crowdmockserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.esolutions.crowdmockserver.entities.CrowdUser;

@Repository
public interface CrowdUserRepository extends JpaRepository<CrowdUser, String> {
    public CrowdUser findAllByUsername(String username);
    public CrowdUser findAllByUsernameAndPassword(String username, String password);
    public void deleteAllByUsername(String username);
}
