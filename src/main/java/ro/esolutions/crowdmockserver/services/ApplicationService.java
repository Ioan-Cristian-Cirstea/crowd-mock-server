package ro.esolutions.crowdmockserver.services;

import java.util.Base64;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import ro.esolutions.crowdmockserver.entities.Application;
import ro.esolutions.crowdmockserver.repositories.ApplicationRepository;
import ro.esolutions.crowdmockserver.json.JsonApplicationList;

@RequiredArgsConstructor
@Service
@Transactional
public class ApplicationService {
    private final ApplicationRepository applicationRepository;

    public JsonApplicationList getAllApplications() {
        return new JsonApplicationList(applicationRepository.findAll());
    }
}
