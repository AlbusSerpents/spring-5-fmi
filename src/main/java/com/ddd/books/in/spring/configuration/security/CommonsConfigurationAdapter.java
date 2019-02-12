package com.ddd.books.in.spring.configuration.security;

import com.ddd.books.in.spring.auth.LibrarianAuthenticationProvider;
import com.ddd.books.in.spring.auth.UsersAuthenticationProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.session.SessionManagementFilter;

import static com.ddd.books.in.spring.configuration.security.SecurityRole.LIBRARIAN;
import static com.ddd.books.in.spring.configuration.security.SecurityRole.USER;
import static org.springframework.http.HttpMethod.OPTIONS;

@Order(3)
@Configuration
public class CommonsConfigurationAdapter extends WebSecurityConfigurerAdapter {

    private final AuthenticationEntryPoint entryPoint;
    private final AuthenticationProvider usersProvider;
    private final AuthenticationProvider librariansProvider;

    public CommonsConfigurationAdapter(
            final AuthenticationEntryPoint entryPoint,
            final UsersAuthenticationProvider usersProvider,
            final LibrarianAuthenticationProvider librariansProvider) {
        this.entryPoint = entryPoint;
        this.usersProvider = usersProvider;
        this.librariansProvider = librariansProvider;
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        auth.authenticationProvider(usersProvider);
        auth.authenticationProvider(librariansProvider);
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .antMatcher("/v1/common/**")
                .addFilterBefore(new CORSFilter(), SessionManagementFilter.class)
                .cors()
                .and()
                .formLogin().disable()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(entryPoint)
                .and()
                .authorizeRequests()
                .antMatchers(OPTIONS).permitAll()
                .antMatchers("/v1/common/**").hasAnyRole(LIBRARIAN.asUserRole(), USER.asUserRole());
    }
}


