package ro.esolutions.crowdmockserver.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ro.esolutions.crowdmockserver.json.JsonAuthenticateRequest;
import ro.esolutions.crowdmockserver.json.JsonAuthenticateResponse;
import ro.esolutions.crowdmockserver.services.CrowdUserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/crowd/rest/usermanagement/1/session")
public class SessionController {
    private final CrowdUserService crowdUserService;
    @PostMapping
    public ResponseEntity<JsonAuthenticateResponse> authenticateUser(@RequestBody JsonAuthenticateRequest authenticateRequest,
                                                                     @RequestParam(value = "validate-password", defaultValue = "true") boolean validatePassword)  {
        String username = authenticateRequest.getUsername();
        String password = authenticateRequest.getPassword();
        System.out.println(username + " " + password);
        if (username == null || password == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        JsonAuthenticateResponse authenticateResponse = crowdUserService.getUserByUsernameAndPassword(username, password);
        if (authenticateResponse == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        return new ResponseEntity<>(authenticateResponse, HttpStatus.CREATED);
    }
}
