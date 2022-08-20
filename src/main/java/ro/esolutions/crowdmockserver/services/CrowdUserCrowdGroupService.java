package ro.esolutions.crowdmockserver.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;
import ro.esolutions.crowdmockserver.entities.CrowdGroup;
import ro.esolutions.crowdmockserver.entities.CrowdUser;
import ro.esolutions.crowdmockserver.entities.CrowdUserCrowdGroup;
import ro.esolutions.crowdmockserver.repositories.CrowdUserRepository;
import ro.esolutions.crowdmockserver.repositories.CrowdUserCrowdGroupRepository;
import ro.esolutions.crowdmockserver.services.CrowdGroupService;

@RequiredArgsConstructor
@Service
@Transactional
public class CrowdUserCrowdGroupService {
    private final CrowdUserRepository crowdUserRepository;
    private final CrowdUserCrowdGroupRepository crowdUserCrowdGroupRepository;
    private final CrowdGroupService crowdGroupService; 

    public HttpStatus addUserToGroup(String appname, String username, String groupname) {
        CrowdUser crowdUser = crowdUserRepository.findAllByUsername(username);
        if (crowdUser == null)
            return HttpStatus.NOT_FOUND;
        CrowdGroup crowdGroup = null;
        try {
            crowdGroup = crowdGroupService.getCrowdGroupByAppNameAndGroupName(appname, groupname);    
        }
        catch(ResponseStatusException responseStatusException) {
            return HttpStatus.FORBIDDEN;
        }
        if (crowdGroup == null)
            return HttpStatus.BAD_REQUEST;
        if (crowdUserCrowdGroupRepository.findByCrowdUser_UUIDAndCrowdGroup_UUID(crowdUser.getUUID(),
                crowdGroup.getUUID()) != null)
            return HttpStatus.CONFLICT;
        crowdUserCrowdGroupRepository.save(new CrowdUserCrowdGroup(crowdUser, crowdGroup));

        return HttpStatus.OK;
    }
}
