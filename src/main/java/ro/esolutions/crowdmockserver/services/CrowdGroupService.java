package ro.esolutions.crowdmockserver.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import ro.esolutions.crowdmockserver.json.JsonGroupDetails;
import ro.esolutions.crowdmockserver.entities.Application;
import ro.esolutions.crowdmockserver.entities.CrowdGroup;
import ro.esolutions.crowdmockserver.entities.ApplicationCrowdGroup;
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

    public JsonGroupDetails getCrowdGroupByName(String appname, String crowdGroupName) {
        Application application = applicationRepository.findAllByName(appname);
        if (application == null)
            return null;
        List<ApplicationCrowdGroup> applicationCrowdGroupList = applicationCrowdGroupRepository
            .findAllByApplication_UUID(application.getUUID());
        List<CrowdGroup> crowdGroupList = crowdGroupRepository.findAllById(applicationCrowdGroupList.stream()
                .map(applicationCrowdGroup -> applicationCrowdGroup.getCrowdGroup().getUUID())
                .collect(Collectors.toList()));
        CrowdGroup crowdGroup = crowdGroupList.stream().filter(group -> group.getName().equals(crowdGroupName))
            .findFirst().orElse(null);
        if (crowdGroup == null)
            return null;
        
        return new JsonGroupDetails(crowdGroup);
    }
}
