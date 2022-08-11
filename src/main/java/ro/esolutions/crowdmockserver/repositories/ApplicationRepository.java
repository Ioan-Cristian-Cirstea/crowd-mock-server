package ro.esolutions.crowdmockserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.esolutions.crowdmockserver.entities.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, String> {
    Application findAllByName(String name);
}
