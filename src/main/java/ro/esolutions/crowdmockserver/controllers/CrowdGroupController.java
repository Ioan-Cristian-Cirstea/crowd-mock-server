package ro.esolutions.crowdmockserver.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ro.esolutions.crowdmockserver.json.JsonGroupDetails;
import ro.esolutions.crowdmockserver.json.JsonNewGroupRequest;
import ro.esolutions.crowdmockserver.services.CrowdGroupService;
import ro.esolutions.crowdmockserver.utilities.Authorization;
import ro.esolutions.crowdmockserver.utilities.ResponseMessage;

import java.util.Base64;

@Controller
@RequiredArgsConstructor
@RequestMapping("/crowd/rest/usermanagement/1/group")
public class CrowdGroupController {
    private final CrowdGroupService crowdGroupService;

    @GetMapping
    public ResponseEntity<?> getGroupByName(@RequestHeader(name = "authorization", required = false) String authorizationHeader,

            @RequestParam(value = "groupname", defaultValue = "") String groupname) {
       if (authorizationHeader == null)
           throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
       Authorization authorization = new Authorization(authorizationHeader);
       // authorization.getUsername() represents the name of the application in this context
       JsonGroupDetails jsonGroupDetails = crowdGroupService.getCrowdGroupByNameAsJson(authorization.getUsername(), groupname);
       if (jsonGroupDetails == null)
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No group with given name");

       return new ResponseEntity(jsonGroupDetails, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addGroup(@RequestBody(required = false) JsonNewGroupRequest jsonNewGroupRequest,
            @RequestHeader(name = "authorization", required = false) String authorizationHeader) {
        Authorization authorization = new Authorization(authorizationHeader);
        // In this case, authorization.getUsername() represents the name of the application
        String applicationName = authorization.getUsername();
        if (jsonNewGroupRequest == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        HttpStatus httpStatus = crowdGroupService.addCrowdGroup(applicationName, jsonNewGroupRequest);
        if (httpStatus == HttpStatus.BAD_REQUEST)
            throw new ResponseStatusException(httpStatus, "Bad request");

        return new ResponseEntity(new JsonGroupDetails(
                    crowdGroupService.getCrowdGroupByName(applicationName, jsonNewGroupRequest.getName())),
                    HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateGroup(@RequestBody(required = false) JsonNewGroupRequest jsonNewGroupRequest,
            @RequestHeader(name = "authorization", required = false) String authorizationHeader,
            @RequestParam(value = "groupname", defaultValue = "") String groupname) {
       Authorization authorization = new Authorization(authorizationHeader);
       if (jsonNewGroupRequest == null)
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");

       return new ResponseEntity(crowdGroupService.updateCrowdGroup(authorization.getUsername(), groupname,
                   jsonNewGroupRequest), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteGroup(@RequestHeader(name = "authorization", required = false) String authorizationHeader,
            @RequestParam(value = "groupname", defaultValue = "") String groupname) {
            if (authorizationHeader == null)
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
            Authorization authorization = new Authorization(authorizationHeader);
            HttpStatus httpStatus = crowdGroupService.deleteCrowdGroup(authorization.getUsername(), groupname);
            if (httpStatus == HttpStatus.UNAUTHORIZED)
                throw new ResponseStatusException(httpStatus, "Unauthorized");
            if (httpStatus == HttpStatus.NOT_FOUND)
                throw new ResponseStatusException(httpStatus, "Not found");

            return new ResponseEntity(new ResponseMessage("Group deleted"), httpStatus);
    }
}
