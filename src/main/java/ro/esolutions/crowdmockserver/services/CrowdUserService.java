package ro.esolutions.crowdmockserver.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.esolutions.crowdmockserver.entities.CrowdUser;
import ro.esolutions.crowdmockserver.json.JsonAuthenticateResponse;
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
        if (crowdUser == null)
            return null;

        return new JsonUserDetails(username, crowdUser.getUUID(), crowdUser.getEmail());
    }

    public JsonAuthenticateResponse getUserByUsernameAndPassword(String username, String password) {
        CrowdUser crowdUser = crowdUserRepository.findAllByUsernameAndPassword(username, password);
        if (crowdUser == null)
            return null;
        return new JsonAuthenticateResponse(username);
    }
}
