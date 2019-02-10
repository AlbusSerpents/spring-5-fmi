package com.ddd.books.in.spring.configuration.security;

import com.ddd.books.in.spring.auth.UsersAuthenticationProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.session.SessionManagementFilter;

import static com.ddd.books.in.spring.configuration.security.SecurityRole.USER;
import static org.springframework.http.HttpMethod.*;

@Order(1)
@Configuration
public class UsersConfigurationAdapter extends WebSecurityConfigurerAdapter {

    private final AuthenticationEntryPoint entryPoint;
    private final AuthenticationProvider provider;

    public UsersConfigurationAdapter(
            final AuthenticationEntryPoint entryPoint,
            final UsersAuthenticationProvider provider) {
        this.entryPoint = entryPoint;
        this.provider = provider;
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        auth.authenticationProvider(provider);
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .antMatcher("/v1/users/**")
                .addFilterBefore(new CORSFilter(), SessionManagementFilter.class)
                .cors()
                .and()
                .formLogin().disable()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(entryPoint)
                .and()
                .authorizeRequests()
                .antMatchers(OPTIONS).permitAll()
                .antMatchers(POST, "/v1/users/login").permitAll()
                .antMatchers(POST, "/v1/users/register").permitAll()
                .antMatchers("/v1/users/**").hasRole(USER.asUserRole());
    }
}
