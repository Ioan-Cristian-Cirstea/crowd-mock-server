package ro.esolutions.crowdmockserver.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ro.esolutions.crowdmockserver.services.CrowdUserCrowdGroupService;
import ro.esolutions.crowdmockserver.utilities.Authorization;
import ro.esolutions.crowdmockserver.utilities.ResponseMessage;
import ro.esolutions.crowdmockserver.json.JsonGroupList;

@RequiredArgsConstructor
@RestController
@RequestMapping("/crowd/rest/usermanagement/1/user/group/direct")
public class CrowdUserCrowdGroupController {
    private final CrowdUserCrowdGroupService crowdUserCrowdGroupService;

    @GetMapping
    public ResponseEntity<?> getUserGroups(@RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "groupname", defaultValue = "") String groupname,
            @RequestHeader(name = "authorization") String authorizationHeader) {
        Authorization authorization = new Authorization(authorizationHeader); 
        JsonGroupList jsonGroupList = crowdUserCrowdGroupService.getUserGroups(authorization.getUsername(),
                username, groupname);
        if (jsonGroupList == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");

        return new ResponseEntity(jsonGroupList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addUserToGroup(@RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "groupname", defaultValue = "") String groupname,
            @RequestHeader(name = "authorization") String authorizationHeader) {
        Authorization authorization = new Authorization(authorizationHeader);
        HttpStatus httpStatus = crowdUserCrowdGroupService.addUserToGroup(authorization.getUsername(),
                username, groupname);
        if (httpStatus == HttpStatus.BAD_REQUEST)
            throw new ResponseStatusException(httpStatus, "Bad request");
        // TODO: add application permissions 
        if (httpStatus == HttpStatus.FORBIDDEN)
            throw new ResponseStatusException(httpStatus, "Forbidden");
        if (httpStatus == HttpStatus.NOT_FOUND)
            throw new ResponseStatusException(httpStatus, "Not found");
        if (httpStatus == HttpStatus.CONFLICT)
            throw new ResponseStatusException(httpStatus, "Conflict");

        return new ResponseEntity(new ResponseMessage("The user was added to the group"), httpStatus);
    }
}