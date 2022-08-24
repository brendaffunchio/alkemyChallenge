package com.backend.disney.controllers;
import com.backend.disney.modelsDTO.AuthenticationRequest;
import com.backend.disney.modelsDTO.AuthenticationResponse;
import com.backend.disney.modelsDTO.UsuarioDTO;
import com.backend.disney.security.JwtUtil;
import com.backend.disney.services.IUsuarioServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private IUsuarioServices userService;
    private UserDetailsService userDetails;
    private JwtUtil jwtUtil;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, IUsuarioServices userService,UserDetailsService userDetails, JwtUtil jwtUtil){
    this.authenticationManager=authenticationManager;
    this.userService = userService;
    this.userDetails=userDetails;
    this.jwtUtil=jwtUtil;
}

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UsuarioDTO usuario) {
        try {
            return new ResponseEntity<>(userService.register(usuario),HttpStatus.CREATED);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> Login(@RequestBody AuthenticationRequest request) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            UserDetails user = userDetails.loadUserByUsername(request.getUsername());
            String jwt = jwtUtil.generateToken(user);
            return new ResponseEntity<>(new AuthenticationResponse(jwt), HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
