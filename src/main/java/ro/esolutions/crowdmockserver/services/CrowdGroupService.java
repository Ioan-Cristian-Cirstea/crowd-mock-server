package ro.esolutions.crowdmockserver.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ro.esolutions.crowdmockserver.json.JsonGroupDetails;
import ro.esolutions.crowdmockserver.json.JsonNewGroupRequest;
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

    public CrowdGroup getCrowdGroupByName(String appname, String crowdGroupName) {
        Application application = applicationRepository.findAllByName(appname);
        List<ApplicationCrowdGroup> applicationCrowdGroupList = applicationCrowdGroupRepository
            .findAllByApplicationUUID(application.getUUID());
        List<CrowdGroup> crowdGroupList = crowdGroupRepository.findAllById(applicationCrowdGroupList.stream()
                .map(applicationCrowdGroup -> applicationCrowdGroup
                    .getCrowdGroup()
                    .getUUID())
                .collect(Collectors.toList()));
        return crowdGroupList.stream().filter(group -> group.getName().equals(crowdGroupName))
            .findFirst().orElse(null);
    }

    public JsonGroupDetails getCrowdGroupByNameAsJson(String appname, String crowdGroupName) {
        CrowdGroup crowdGroup = this.getCrowdGroupByName(appname, crowdGroupName);

        return crowdGroup == null ? null : new JsonGroupDetails(crowdGroup);
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
                            applicationRepository.findAllByName(appname),
                            crowdGroup));

        return HttpStatus.CREATED;
    }

    public JsonGroupDetails updateCrowdGroup(String appname, String groupname,
            JsonNewGroupRequest jsonNewGroupRequest) {
        CrowdGroup crowdGroup = getCrowdGroupByAppNameAndGroupName(appname, groupname);
        if (crowdGroup == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        updateCrowdGroupInfo(jsonNewGroupRequest, crowdGroup);

        return new JsonGroupDetails(crowdGroup);
    }

    public HttpStatus deleteCrowdGroup(String appname, String groupname) {
        CrowdGroup crowdGroup = getCrowdGroupByAppNameAndGroupName(appname, groupname);
        if (crowdGroup == null)
            return HttpStatus.NOT_FOUND;
        applicationCrowdGroupRepository.deleteAllByCrowdGroup_UUID(crowdGroup.getUUID());
        crowdGroupRepository.deleteById(crowdGroup.getUUID());

        return HttpStatus.NO_CONTENT;
    }

    public CrowdGroup getCrowdGroupByAppNameAndGroupName(String appname, String groupname) {
        Application application = applicationRepository.findAllByName(appname);
        if (application == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        return crowdGroupRepository.findAllById(
                applicationCrowdGroupRepository.findAllByApplicationUUID(application.getUUID())
                    .stream()
                    .map(applicationCrowdGroup -> applicationCrowdGroup.getCrowdGroup().getUUID())
                    .collect(Collectors.toList()))
            .stream()
            .filter(group -> group.getName().equals(groupname))
            .findFirst().orElse(null);
    }

    private void updateCrowdGroupInfo(JsonNewGroupRequest jsonNewGroupRequest, CrowdGroup crowdGroup) {
        if (jsonNewGroupRequest.getName() != null)
            crowdGroup.setName(jsonNewGroupRequest.getName());
        if (jsonNewGroupRequest.getDescription() != null)
            crowdGroup.setDescription(jsonNewGroupRequest.getDescription());
    }
}
