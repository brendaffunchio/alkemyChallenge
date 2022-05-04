package com.backend.disney.Controllers;

import com.backend.disney.Models.Usuario;
import com.backend.disney.Security.JwtUtil;
import com.backend.disney.Services.IUsuarioServices;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    IUsuarioServices usuarioServices;

    @Autowired
    JwtUtil jwtUtil;


    @PostMapping("/register")
    public Usuario register(@RequestBody Usuario usuario, HttpServletResponse httpServletResponse) {
        try {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            usuarioServices.register(usuario);
            return usuario;
        } catch (Exception e) {
            httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            return null;
        }
    }

    @PostMapping(path="/login")
    public String login (@RequestBody @NotNull Usuario usuario)throws Exception {
        System.out.println("hola");
        Usuario user = usuarioServices.login(usuario.getUsername(), usuario.getPassword());
        if (user != null) {
            String token = jwtUtil.generateToken(user);
            return token;
        } else {
            return "Cannot login";
        }
    }
}
