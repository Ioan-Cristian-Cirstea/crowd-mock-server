package ro.esolutions.crowdmockserver.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.esolutions.crowdmockserver.entities.CrowdUser;
import ro.esolutions.crowdmockserver.entities.Token;
import ro.esolutions.crowdmockserver.json.JsonAuthenticateResponse;
import ro.esolutions.crowdmockserver.json.JsonUserDetails;
import ro.esolutions.crowdmockserver.json.JsonUserList;
import ro.esolutions.crowdmockserver.repositories.CrowdUserRepository;
import ro.esolutions.crowdmockserver.repositories.TokenRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CrowdUserService {
    private final CrowdUserRepository crowdUserRepository;
    private final TokenService tokenService;
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

        return new JsonUserDetails(username, crowdUser.getUUID(), crowdUser.getEmail());
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
}
