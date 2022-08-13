package ro.esolutions.crowdmockserver.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ro.esolutions.crowdmockserver.json.JsonGroupDetails;
import ro.esolutions.crowdmockserver.services.CrowdGroupService;

import java.util.Base64;

@Controller
@RequiredArgsConstructor
@RequestMapping("/crowd/rest/usermanagement/1/group")
public class CrowdGroupController {
    private final CrowdGroupService crowdGroupService;

    @GetMapping
    public ResponseEntity<?> getGroupByName(@RequestHeader(name = "authorization", required = false) String authorization, @RequestParam(value = "groupname", defaultValue = "") String groupname) {
       if (authorization == null)
           throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
       //TODO: add appname to getGroupByNameCall
       JsonGroupDetails jsonGroupDetails = crowdGroupService.getCrowdGroupByName(groupname, groupname);
       if (jsonGroupDetails == null)
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No group with given name");

       return new ResponseEntity(jsonGroupDetails, HttpStatus.OK);
    }
}
