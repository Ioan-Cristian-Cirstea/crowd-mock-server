package ro.esolutions.crowdmockserver.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ro.esolutions.crowdmockserver.utilities.ResponseMessage;
import ro.esolutions.crowdmockserver.json.JsonApplicationList;
import ro.esolutions.crowdmockserver.services.ApplicationService;


@Controller
@RequiredArgsConstructor
@RequestMapping("/crowd/rest/appmanagement/1/application")
public class ApplicationController {
    private final ApplicationService applicationService;

    @GetMapping
    public ResponseEntity<?> authenticateIntoApplication() {
        JsonApplicationList applicationList = applicationService.getAllApplications();

        return new ResponseEntity<>(applicationList, HttpStatus.OK);
    }
}
