package ro.esolutions.crowdmockserver.utilities;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.esolutions.crowdmockserver.repositories.ApplicationRepository;

@Component
@RequiredArgsConstructor
public class CheckCredentials {
    private final ApplicationRepository applicationRepository;

    public boolean checkApplication(String appName, String appPassword) {
        return applicationRepository.findAllByNameAndPassword(appName, appPassword) != null;
    }

    public boolean checkAdmin(String adminName, String adminPassword) {
        return adminName.equals("admin") && adminPassword.equals("admin");
    }
}
