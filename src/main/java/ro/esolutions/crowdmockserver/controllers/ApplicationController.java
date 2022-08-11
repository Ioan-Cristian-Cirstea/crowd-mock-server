package ro.esolutions.crowdmockserver.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.esolutions.crowdmockserver.utilities.ResponseMessage;


@Controller
@RequestMapping("/crowd/rest/appmanagement/1/application")
public class ApplicationController {
    @GetMapping
    public ResponseEntity<ResponseMessage> approveApplication() {
        return new ResponseEntity<>(new ResponseMessage("Authorized"), HttpStatus.OK);
    }
}
