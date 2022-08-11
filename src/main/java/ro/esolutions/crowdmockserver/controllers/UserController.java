package ro.esolutions.crowdmockserver.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ro.esolutions.crowdmockserver.json.JsonNewUserRequest;
import ro.esolutions.crowdmockserver.json.JsonUserDetails;
import ro.esolutions.crowdmockserver.services.CrowdUserService;
import ro.esolutions.crowdmockserver.utilities.ResponseMessage;
import ro.esolutions.crowdmockserver.utilities.ReturnJsonUserDetails;

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

    @DeleteMapping
    public ResponseEntity<ResponseMessage> deleteUser(@RequestParam(value = "username", defaultValue = "") String username) {
        ResponseMessage responseMessage = crowdUserService.deleteUser(username);
        HttpStatus httpStatus;
        if (responseMessage.getMessage().equals("User not found"))
            httpStatus = HttpStatus.NOT_FOUND;
        else
            httpStatus = HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(responseMessage, httpStatus);
    }

    @PostMapping
    public ResponseEntity<JsonUserDetails> addUser(@RequestBody JsonNewUserRequest jsonNewUserRequest) {
        JsonUserDetails jsonUserDetails = crowdUserService.addUser(jsonNewUserRequest);
        if (jsonUserDetails == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");

        return new ResponseEntity<>(jsonUserDetails, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Object> updateUser(@RequestBody JsonNewUserRequest jsonNewUserRequest,
                                                      @RequestParam(value = "username", defaultValue = "") String username) {
        HttpStatus httpStatus = crowdUserService.updateUser(jsonNewUserRequest, username);
        if (httpStatus == HttpStatus.NOT_FOUND)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");

        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}