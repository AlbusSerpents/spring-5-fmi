package com.ddd.books.in.spring.rest;

import com.ddd.books.in.spring.auth.CustomUserDetails;
import com.ddd.books.in.spring.func.exceptions.FunctionalException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static com.ddd.books.in.spring.func.exceptions.ErrorResponse.ErrorCode.TEST_CODE;
import static org.springframework.http.HttpStatus.I_AM_A_TEAPOT;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class SecuredEndpointTest {

    @RequestMapping(value = "/v1/secured", method = GET)
    public Map<String, String> test(final @AuthenticationPrincipal CustomUserDetails details) {
        final Map<String, String> map = new HashMap<>();
        System.out.println(details);
        map.put("name", details.getUser().getName());
        map.put("pesho", "pesho");

        return map;
    }

    @ResponseStatus(NO_CONTENT)
    @RequestMapping(value = "/v1/secured/error", method = GET)
    public void testException(final @AuthenticationPrincipal CustomUserDetails details) {
        throw new FunctionalException(TEST_CODE, "No message", I_AM_A_TEAPOT);
    }
}
