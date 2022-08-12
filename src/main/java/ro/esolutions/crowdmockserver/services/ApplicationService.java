package ro.esolutions.crowdmockserver.services;

import java.util.Base64;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import ro.esolutions.crowdmockserver.entities.Application;
import ro.esolutions.crowdmockserver.repositories.ApplicationRepository;
import ro.esolutions.crowdmockserver.json.JsonApplicationList;
import ro.esolutions.crowdmockserver.json.JsonNewApplicationRequest;
import ro.esolutions.crowdmockserver.json.JsonPassword;

@RequiredArgsConstructor
@Service
@Transactional
public class ApplicationService {
    private final ApplicationRepository applicationRepository;

    public JsonApplicationList getAllApplicationsAsJson() {
        return new JsonApplicationList(applicationRepository.findAll());
    }

    public Application getApplicationByName(String name) {
        return applicationRepository.findAllByName(name);
    }

    public HttpStatus addApplication(JsonNewApplicationRequest jsonNewApplicationRequest) {
        if (!checkNewApplicationRequest(jsonNewApplicationRequest))
            return HttpStatus.BAD_REQUEST;
        String name = jsonNewApplicationRequest.getName();
        String password = jsonNewApplicationRequest.getPassword().getValue();
        Application application = getApplicationByName(name);
        if (application != null)
            return HttpStatus.NOT_MODIFIED;
        applicationRepository.save(new Application(name, password));

        return HttpStatus.CREATED;
    }

    private boolean checkNewApplicationRequest(JsonNewApplicationRequest jsonNewApplicationRequest) {
        return checkString(jsonNewApplicationRequest.getName()) && checkJsonPassword(jsonNewApplicationRequest.getPassword());
    }

    private boolean checkString(final String string) {
        return string != null && !string.equals("");
    }

    private boolean checkJsonPassword(final JsonPassword jsonPassword) {
        return jsonPassword != null && checkString(jsonPassword.getValue());
    }

    public HttpStatus deleteApplicationByName(String applicationName) {
        Application application = applicationRepository.findAllByName(applicationName);
        if (application == null)
            return HttpStatus.NOT_FOUND;
        applicationRepository.delete(application);

        return HttpStatus.NO_CONTENT;
    }
}
