package ro.esolutions.crowdmockserver.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import ro.esolutions.crowdmockserver.json.JsonGroupDetails;
import ro.esolutions.crowdmockserver.json.JsonNewGroupRequest;
import ro.esolutions.crowdmockserver.entities.Application;
import ro.esolutions.crowdmockserver.entities.CrowdGroup;
import ro.esolutions.crowdmockserver.entities.ApplicationCrowdGroup;
import ro.esolutions.crowdmockserver.entities.ApplicationCrowdGroupKey;
import ro.esolutions.crowdmockserver.repositories.CrowdGroupRepository;
import ro.esolutions.crowdmockserver.repositories.ApplicationRepository;
import ro.esolutions.crowdmockserver.repositories.ApplicationCrowdGroupRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class CrowdGroupService {
    private final CrowdGroupRepository crowdGroupRepository;
    private final ApplicationRepository applicationRepository;
    private final ApplicationCrowdGroupRepository applicationCrowdGroupRepository;

    public CrowdGroup getCrowdGroupByName(String appname, String crowdGroupName) {
        Application application = applicationRepository.findAllByName(appname);
        List<ApplicationCrowdGroup> applicationCrowdGroupList = applicationCrowdGroupRepository
            .findAllByApplicationCrowdGroupKey_Application_UUID(application.getUUID());
        List<CrowdGroup> crowdGroupList = crowdGroupRepository.findAllById(applicationCrowdGroupList.stream()
                .map(applicationCrowdGroup -> applicationCrowdGroup
                    .getApplicationCrowdGroupKey()
                    .getCrowdGroup()
                    .getUUID())
                .collect(Collectors.toList()));
        return crowdGroupList.stream().filter(group -> group.getName().equals(crowdGroupName))
            .findFirst().orElse(null);
    }

    public JsonGroupDetails getCrowdGroupByNameAsJson(String appname, String crowdGroupName) {
        CrowdGroup crowdGroup = this.getCrowdGroupByName(appname, crowdGroupName);
        if (crowdGroup == null)
            return null;
        
        return new JsonGroupDetails(crowdGroup);
    }

    public HttpStatus addCrowdGroup(String appname, JsonNewGroupRequest jsonNewGroupRequest) {
        if (jsonNewGroupRequest.getName() == null || jsonNewGroupRequest.getName().equals(""))
            return HttpStatus.BAD_REQUEST;
        CrowdGroup crowdGroup = this.getCrowdGroupByName(appname, jsonNewGroupRequest.getName());
        if (crowdGroup != null)
            return HttpStatus.BAD_REQUEST;
        crowdGroup = crowdGroupRepository.save(new CrowdGroup(jsonNewGroupRequest));
        applicationCrowdGroupRepository.save(
                new ApplicationCrowdGroup(
                    new ApplicationCrowdGroupKey(
                            applicationRepository.findAllByName(appname),
                            crowdGroup)));

        return HttpStatus.CREATED;
    }
}
