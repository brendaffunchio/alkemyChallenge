package com.backend.disney.Controllers;

import com.backend.disney.Models.Usuario;
import com.backend.disney.ModelsDTO.AuthenticationRequest;
import com.backend.disney.ModelsDTO.AuthenticationResponse;
import com.backend.disney.ModelsDTO.UsuarioDtoResponse;
import com.backend.disney.Security.JwtUtil;
import com.backend.disney.Services.IUsuarioServices;
import com.backend.disney.Services.impl.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    IUsuarioServices usuarioService;

    @Autowired
    UsuarioService userService;

    @Autowired
    JwtUtil jwtUtil;


    @PostMapping("/register")
    public Usuario register(@RequestBody Usuario usuario, @RequestParam("rol") String rol, HttpServletResponse httpServletResponse) {
        try {
            httpServletResponse.setStatus(HttpStatus.OK.value());
           return usuarioService.register(usuario,rol);

        } catch (Exception e) {
            httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            return null;
        }
    }

    /*@PostMapping(path = "/login")
    public String login(@RequestBody @NotNull Usuario usuario, HttpServletResponse httpServletResponse) {
        try {
            Usuario user = usuarioServices.login(usuario);
            httpServletResponse.setStatus(HttpStatus.OK.value());
            String token = jwtUtil.generateToken(user);
            return token;

        } catch (Exception e) {
            httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            return e.getMessage();
        }

    }*/
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> Login(@RequestBody AuthenticationRequest request) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            UserDetails userDetails = userService.loadUserByUsername(request.getUsername());
            String jwt = jwtUtil.generateToken(userDetails);
            return new ResponseEntity<>(new AuthenticationResponse(jwt), HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
