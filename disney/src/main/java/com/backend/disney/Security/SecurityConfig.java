package com.backend.disney.Security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
@EnableWebMvc
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
              .antMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .antMatchers(HttpMethod.POST, "/auth/register").permitAll()

                .anyRequest().authenticated();


    }
/*
        @Override
        protected void configure(HttpSecurity http) throws Exception {
                http.csrf().disable().authorizeRequests()
                        .antMatchers("/login").permitAll()
                        .anyRequest().authenticated()
                        .and();/*
        .addFilterBefore(newLoginFilter("/login", authenticationManager()),
        UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(newJwtFilter(),
        UsernamePasswordAuthenticationFilter.class);

        }*/
}