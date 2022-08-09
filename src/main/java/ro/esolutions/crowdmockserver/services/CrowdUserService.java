package ro.esolutions.crowdmockserver.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.esolutions.crowdmockserver.entities.CrowdUser;
import ro.esolutions.crowdmockserver.json.JsonUserDetails;
import ro.esolutions.crowdmockserver.json.JsonUserList;
import ro.esolutions.crowdmockserver.repositories.CrowdUserRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CrowdUserService {
    private final CrowdUserRepository crowdUserRepository;
    public JsonUserList getAllUsers() {
        List<CrowdUser> crowdUserList = crowdUserRepository.findAll();
        return new JsonUserList(crowdUserList.stream()
                .map(CrowdUser::getUsername)
                .collect(Collectors.toList()));
    }

    public JsonUserDetails getUserByUsername(String username) {
        CrowdUser crowdUser = crowdUserRepository.findAllByUsername(username);

        return new JsonUserDetails(username, crowdUser.getUUID(), crowdUser.getEmail());
    }
}
