package com.procurement.point.controller;

import com.procurement.point.service.PublicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicController {

    private final PublicService publicService;

    public PublicController(final PublicService publicService) {
        this.publicService = publicService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/get/{fileId}")
    public ResponseEntity<String> getFile(@PathVariable(value = "fileId") final String fileId) {
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
}
