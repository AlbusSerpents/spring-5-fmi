package com.ddd.books.in.spring.rest;

import com.ddd.books.in.spring.func.status.ApplicationStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class StatusEndpoint {

    @RequestMapping(value = "/status", method = GET)
    public ApplicationStatus getStatus() {
        return new ApplicationStatus("Books in configuration", "1.0.0", "OK");
    }

}
