package ro.esolutions.crowdmockserver.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.esolutions.crowdmockserver.entities.CrowdUser;
import ro.esolutions.crowdmockserver.entities.Token;
import ro.esolutions.crowdmockserver.json.*;
import ro.esolutions.crowdmockserver.repositories.CrowdUserRepository;
import ro.esolutions.crowdmockserver.utilities.ResponseMessage;
import ro.esolutions.crowdmockserver.utilities.ReturnJsonUserDetails;

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

    private boolean checkString(String field) {
        return field != null && !field.equals("");
    }

    private boolean checkJsonPassword(JsonPassword jsonPassword) {
        return jsonPassword != null && jsonPassword.getValue() != null
                && !jsonPassword.getValue().equals("");
    }

    public JsonUserDetails addUser(@NonNull JsonNewUserRequest jsonNewUserRequest) {
        if (!checkString(jsonNewUserRequest.getName()))
            return null;
        if (!checkJsonPassword(jsonNewUserRequest.getPassword()))
            return null;
        if (!checkString(jsonNewUserRequest.getEmail()))
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

    public HttpStatus updateUser(@NonNull JsonNewUserRequest jsonNewUserRequest, String username) {
        CrowdUser crowdUser = crowdUserRepository.findAllByUsername(username);
        if (crowdUser == null)
            return HttpStatus.NOT_FOUND;
        if (checkString(jsonNewUserRequest.getName()))
            crowdUser.setUsername(jsonNewUserRequest.getName());
        if (checkString(jsonNewUserRequest.getFirst_name()))
            crowdUser.setFirstName(jsonNewUserRequest.getFirst_name());
        if (checkString(jsonNewUserRequest.getLast_name()))
            crowdUser.setLastName(jsonNewUserRequest.getLast_name());
        if (checkString(jsonNewUserRequest.getDisplay_name()))
            crowdUser.setDisplayName(jsonNewUserRequest.getDisplay_name());
        if (checkString(jsonNewUserRequest.getEmail()))
            crowdUser.setEmail(jsonNewUserRequest.getEmail());
        crowdUserRepository.save(crowdUser);

        return HttpStatus.NO_CONTENT;
    }

    public HttpStatus deleteUserPassword(String username) {
        CrowdUser crowdUser = crowdUserRepository.findAllByUsername(username);
        if (crowdUser == null)
            return HttpStatus.NOT_FOUND;
        crowdUser.setPassword("");
        crowdUserRepository.save(crowdUser);

        return HttpStatus.NO_CONTENT;
    }
}
