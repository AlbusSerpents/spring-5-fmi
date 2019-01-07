package com.ddd.books.in.spring.rest;

import com.ddd.books.in.spring.auth.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

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
}
