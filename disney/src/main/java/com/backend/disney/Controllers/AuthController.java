package com.backend.disney.Controllers;
import com.backend.disney.Models.Usuario;
import com.backend.disney.Services.IAuthService;
import com.backend.disney.Services.IEmailService;
import com.backend.disney.Services.IUsuarioServices;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    IEmailService emailService;
    @Autowired
    IUsuarioServices usuarioServices;


    @PostMapping("/register")
    public String register(@RequestBody Usuario usuario){

        try {
            Usuario user = usuarioServices.register(usuario);
           // return "ok";
            return emailService.sendEmail(usuario.getUsername());
        }catch(Exception e){
            return "cannot register and send email";
        }
    }

    @PostMapping(path="/login",consumes = "application/json", produces = "application/json")
    public Usuario login(@RequestParam("username") String username,@RequestParam("password")String password)
            throws Exception {
        System.out.println("hola");
    Usuario user = usuarioServices.login(username,password);
    if(user!=null){
        String token = getJWTToken(username);
    }

    return user;

}



    private String getJWTToken(String username) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("disneyJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }
}
