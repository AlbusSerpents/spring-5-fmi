package com.ddd.books.in.spring.rest.free;

import com.ddd.books.in.spring.func.status.ApplicationStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.I_AM_A_TEAPOT;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class StatusEndpoint {

    @ResponseStatus(I_AM_A_TEAPOT)
    @RequestMapping(value = "/status", method = GET)
    public ApplicationStatus getStatus() {
        return new ApplicationStatus("Books in spring", "1.0.0", "OK");
    }

}
