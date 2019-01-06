package com.ddd.books.in.spring.configuration.security;

import com.ddd.books.in.spring.auth.AdminAuthenticationProvider;
import com.ddd.books.in.spring.auth.UsersAuthenticationProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;

import static com.ddd.books.in.spring.configuration.security.SecurityRole.ADMIN;
import static com.ddd.books.in.spring.configuration.security.SecurityRole.USER;
import static org.springframework.http.HttpMethod.OPTIONS;

@Configuration
public class UsersConfigurationAdapter extends WebSecurityConfigurerAdapter {

    private final AuthenticationEntryPoint entryPoint;
    private final AuthenticationProvider usersProvider;
    private final AuthenticationProvider adminsProvider;

    public UsersConfigurationAdapter(
            final AuthenticationEntryPoint entryPoint,
            final UsersAuthenticationProvider usersProvider,
            final AdminAuthenticationProvider adminsProvider) {
        this.entryPoint = entryPoint;
        this.usersProvider = usersProvider;
        this.adminsProvider = adminsProvider;
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        auth.authenticationProvider(usersProvider);
        auth.authenticationProvider(adminsProvider);
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .antMatcher("/**")
                .cors()
                .and()
                .formLogin().disable()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(entryPoint)
                .and()
                .authorizeRequests()
                .antMatchers("/authenticated/**").hasRole(USER.asUserRole())
                .antMatchers("/admin/**").hasRole(ADMIN.asUserRole())
                .antMatchers(OPTIONS, ".*").permitAll()
                .antMatchers(".*").permitAll();
    }
}
