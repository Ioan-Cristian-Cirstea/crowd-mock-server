package ro.esolutions.crowdmockserver.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.esolutions.crowdmockserver.entities.CrowdUser;
import ro.esolutions.crowdmockserver.entities.Token;
import ro.esolutions.crowdmockserver.json.JsonAuthenticateResponse;
import ro.esolutions.crowdmockserver.json.JsonNewUserRequest;
import ro.esolutions.crowdmockserver.json.JsonUserDetails;
import ro.esolutions.crowdmockserver.json.JsonUserList;
import ro.esolutions.crowdmockserver.repositories.CrowdUserRepository;
import ro.esolutions.crowdmockserver.utilities.ResponseMessage;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class CrowdUserService {
    private final CrowdUserRepository crowdUserRepository;
    private final TokenService tokenService;

    public ResponseMessage deleteUser(String username) {
        CrowdUser crowdUser = crowdUserRepository.findAllByUsername(username);
        if (crowdUser == null)
            return new ResponseMessage("User not found");
        tokenService.deleteTokensByUserUUID(crowdUser.getUUID());
        crowdUserRepository.deleteAllByUsername(username);
        return new ResponseMessage("User deleted");
    }
    public JsonUserList getAllUsersAsJsonList() {
        List<CrowdUser> crowdUserList = crowdUserRepository.findAll();
        return new JsonUserList(crowdUserList.stream()
                .map(CrowdUser::getUsername)
                .collect(Collectors.toList()));
    }

    public JsonUserDetails getUserByUsernameAsJsonUserDetails(String username) {
        CrowdUser crowdUser = crowdUserRepository.findAllByUsername(username);
        if (crowdUser == null)
            return null;

        return new JsonUserDetails(crowdUser);
    }

    public JsonAuthenticateResponse getJsonAuthenticateResponse(String username, String password) {
        CrowdUser crowdUser = crowdUserRepository.findAllByUsernameAndPassword(username, password);
        if (crowdUser == null)
            return null;
        Token token = tokenService.findTokenByUserUUID(crowdUser.getUUID());
        if (token == null) {
            JsonAuthenticateResponse authenticateResponse = new JsonAuthenticateResponse(username);
            tokenService.saveToken(authenticateResponse.getToken(),
                    authenticateResponse.getCreated_date(),
                    authenticateResponse.getExpire_date(),
                    crowdUser);

            return authenticateResponse;
        }
        return new JsonAuthenticateResponse(username, token);
    }

    public JsonUserDetails addUser(JsonNewUserRequest jsonNewUserRequest) {
        if (jsonNewUserRequest.getName() == null)
            return null;
        if (jsonNewUserRequest.getPassword() == null)
            return null;
        if (jsonNewUserRequest.getEmail() == null)
            return null;
        if (jsonNewUserRequest.getName().equals(""))
            return null;
        if (jsonNewUserRequest.getPassword().getValue().equals(""))
            return null;
        if (jsonNewUserRequest.getEmail().equals(""))
            return null;
        if (crowdUserRepository.findAllByUsername(jsonNewUserRequest.getName()) != null)
            return null;

        CrowdUser crowdUser = CrowdUser.builder()
                .username(jsonNewUserRequest.getName())
                .password(jsonNewUserRequest.getPassword().getValue())
                .email(jsonNewUserRequest.getEmail())
                .firstName(jsonNewUserRequest.getFirst_name())
                .lastName(jsonNewUserRequest.getLast_name())
                .displayName(jsonNewUserRequest.getDisplay_name())
                .build();
        crowdUserRepository.save(crowdUser);

        return new JsonUserDetails(crowdUser);
    }
}
