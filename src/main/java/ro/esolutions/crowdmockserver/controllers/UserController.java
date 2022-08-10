package ro.esolutions.crowdmockserver.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ro.esolutions.crowdmockserver.json.JsonUserDetails;
import ro.esolutions.crowdmockserver.services.CrowdUserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/crowd/rest/usermanagement/1/user")
public class UserController {
    private final CrowdUserService crowdUserService;

    @GetMapping
    public ResponseEntity<JsonUserDetails> getUser(@RequestParam(value = "username", defaultValue = "") String username) {
        if (username.equals(""))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");

        JsonUserDetails userDetails = crowdUserService.getUserByUsernameAsJsonUserDetails(username);
        if (userDetails == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        return new ResponseEntity<>(userDetails, HttpStatus.OK);
    }
}