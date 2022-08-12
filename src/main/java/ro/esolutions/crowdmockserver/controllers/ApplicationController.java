package ro.esolutions.crowdmockserver.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ro.esolutions.crowdmockserver.utilities.ResponseMessage;
import ro.esolutions.crowdmockserver.json.JsonApplicationList;
import ro.esolutions.crowdmockserver.json.JsonNewApplicationRequest;
import ro.esolutions.crowdmockserver.json.JsonApplicationSummary;
import ro.esolutions.crowdmockserver.services.ApplicationService;


@Controller
@RequiredArgsConstructor
@RequestMapping("/crowd/rest/appmanagement/1/application")
public class ApplicationController {
    private final ApplicationService applicationService;

    @GetMapping
    public ResponseEntity<?> getAllApplications() {
        JsonApplicationList applicationList = applicationService.getAllApplicationsAsJson();
        return new ResponseEntity<>(applicationList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createApplication(@RequestBody JsonNewApplicationRequest jsonNewApplicationRequest) {
       HttpStatus httpStatus = applicationService.addApplication(jsonNewApplicationRequest); 
       if (httpStatus == HttpStatus.NOT_MODIFIED)
           throw new ResponseStatusException(httpStatus, "An app with this name already exists");
       else if (httpStatus == HttpStatus.BAD_REQUEST)
           throw new ResponseStatusException(httpStatus, "Bad request");

       return new ResponseEntity(new JsonApplicationSummary(applicationService.getApplicationByName(jsonNewApplicationRequest.getName())), httpStatus);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteApplication(@RequestParam(value = "appname", defaultValue = "") String applicationName) {
        if (applicationName.equals(""))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        HttpStatus httpStatus = applicationService.deleteApplicationByName(applicationName);
        if (httpStatus == HttpStatus.NOT_FOUND)
            return new ResponseEntity<>(new ResponseMessage("No application with given name"), httpStatus);

        return new ResponseEntity<>(null, httpStatus);
    }

    @PutMapping
    public ResponseEntity<?> updateApplication(@RequestBody JsonNewApplicationRequest jsonNewApplicationRequest,
            @RequestParam(value = "appname", defaultValue = "") String applicationName) {
        if (applicationName.equals(""))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        HttpStatus httpStatus = applicationService.updateApplicationByName(jsonNewApplicationRequest, applicationName);
        if (httpStatus == HttpStatus.NOT_FOUND)
            throw new ResponseStatusException(httpStatus, "Not found");

        return new ResponseEntity<>(null, httpStatus);
    }
}
