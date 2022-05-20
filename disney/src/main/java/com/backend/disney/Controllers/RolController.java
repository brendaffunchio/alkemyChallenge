package com.backend.disney.Controllers;

import com.backend.disney.Models.Genero;
import com.backend.disney.Models.Rol;
import com.backend.disney.Services.IRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/role")
public class RolController {

@Autowired
    IRolService service;


    @PostMapping(path = "/create")
    public String createRole(@RequestBody Rol rol, HttpServletResponse httpServletResponse) {
        try {

            httpServletResponse.setStatus(HttpStatus.OK.value());
            service.create(rol);
            return "Role created successfully ";

        } catch (Exception e) {
            httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            return e.getMessage();
        }
    }

}
