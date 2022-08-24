package com.backend.disney.security;

import com.backend.disney.services.impl.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioService usuarioService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .antMatchers(HttpMethod.POST, "/auth/register").permitAll()
                .antMatchers(HttpMethod.POST,"/role/create").permitAll()
                .antMatchers(HttpMethod.POST, "/genre/create").permitAll()
                .antMatchers(HttpMethod.POST, "/movies/create").permitAll()
                .antMatchers(HttpMethod.PUT, "/movies/edit").permitAll()
                .antMatchers(HttpMethod.DELETE, "/movies/delete").permitAll()
                .antMatchers(HttpMethod.GET, "/movies/getDetails").permitAll()
                .antMatchers(HttpMethod.GET, "/movies/").permitAll()
                .antMatchers(HttpMethod.POST, "/movies/{idMovie}/characters/{idCharacter}").permitAll()
                .antMatchers(HttpMethod.DELETE, "/movies/{movieId}/characters/{characterId}").permitAll()
                .antMatchers(HttpMethod.POST, "/characters/create").permitAll()
                .antMatchers(HttpMethod.PUT, "/characters/edit").permitAll()
                .antMatchers(HttpMethod.DELETE, "/characters/delete").permitAll()
                .antMatchers(HttpMethod.GET, "/characters/getDetails").permitAll()
                .antMatchers(HttpMethod.GET, "/characters/").permitAll()
                .anyRequest().authenticated().and();


    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean("authenticationManager")
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

}