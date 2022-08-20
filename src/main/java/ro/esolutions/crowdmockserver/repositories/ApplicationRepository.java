package ro.esolutions.crowdmockserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.esolutions.crowdmockserver.entities.Application;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, String> {
    public List<Application> findAll();
    public Application findAllByName(String name);
    public Application findAllByNameAndPassword(String name, String password);
}
