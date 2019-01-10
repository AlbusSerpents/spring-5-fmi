package com.ddd.books.in.spring.configuration.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.session.SessionManagementFilter;

import static org.springframework.http.HttpMethod.GET;

@Order(4)
@Configuration
public class PublicConfigurationAdapter extends WebSecurityConfigurerAdapter {

    private final AuthenticationEntryPoint entryPoint;

    public PublicConfigurationAdapter(
            final AuthenticationEntryPoint entryPoint) {
        this.entryPoint = entryPoint;
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .antMatcher("/**")
                .addFilterBefore(new CORSFilter(), SessionManagementFilter.class)
                .cors()
                .and()
                .formLogin().disable()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(entryPoint)
                .and()
                .authorizeRequests()
                .antMatchers(GET, "/status").permitAll()
                .antMatchers("/v1/public").permitAll();
    }
}



