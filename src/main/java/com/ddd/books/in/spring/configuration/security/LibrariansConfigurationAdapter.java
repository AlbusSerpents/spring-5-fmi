package com.ddd.books.in.spring.configuration.security;

import com.ddd.books.in.spring.auth.LibrarianAuthenticationProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.session.SessionManagementFilter;

import static com.ddd.books.in.spring.configuration.security.SecurityRole.LIBRARIAN;
import static org.springframework.http.HttpMethod.POST;

@Order(2)
@Configuration
public class LibrariansConfigurationAdapter extends WebSecurityConfigurerAdapter {

    private final AuthenticationEntryPoint entryPoint;
    private final AuthenticationProvider provider;

    public LibrariansConfigurationAdapter(
            final AuthenticationEntryPoint entryPoint,
            final LibrarianAuthenticationProvider provider) {
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
                .antMatcher("/v1/librarians/**")
                .addFilterBefore(new CORSFilter(), SessionManagementFilter.class)
                .cors()
                .and()
                .formLogin().disable()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(entryPoint)
                .and()
                .authorizeRequests()
                .antMatchers(POST, "/v1/librarians/login").permitAll()
                .antMatchers("/v1/librarians/**").hasRole(LIBRARIAN.asUserRole());
    }
}

