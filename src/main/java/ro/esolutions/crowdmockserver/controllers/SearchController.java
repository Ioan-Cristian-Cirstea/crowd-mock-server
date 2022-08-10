package ro.esolutions.crowdmockserver.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ro.esolutions.crowdmockserver.json.JsonUserList;
import ro.esolutions.crowdmockserver.services.CrowdUserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/crowd/rest/usermanagement/1/search")
public class SearchController {
    private final CrowdUserService crowdUserService;
    @GetMapping
    public ResponseEntity<JsonUserList> getAllUsers(@RequestParam(value = "entity-type", defaultValue = "") String entityType) {
        if (!entityType.equals("user"))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        JsonUserList userList = crowdUserService.getAllUsersAsJsonList();

        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
}
