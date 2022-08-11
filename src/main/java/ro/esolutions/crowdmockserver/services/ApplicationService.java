package ro.esolutions.crowdmockserver.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.esolutions.crowdmockserver.entities.Application;
import ro.esolutions.crowdmockserver.repositories.ApplicationRepository;

@RequiredArgsConstructor
@Service
@Transactional
public class ApplicationService {
    private final ApplicationRepository applicationRepository;

    public Application getApplicationByName(String name) {
        return applicationRepository.findAllByName(name);
    }
}
