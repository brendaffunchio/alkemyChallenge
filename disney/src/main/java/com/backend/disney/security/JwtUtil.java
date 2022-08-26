package com.backend.disney.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    /* JSON Based Token (JWT, https://jwt.io/) es un estándar de código abierto basado en JSON
    para crear tokens de acceso que nos permiten securizar las comunicaciones entre cliente y servidor
             ¿Cómo funciona?
             1. El cliente se autentica y garantiza su identidad haciendo una petición al servidor
              de autenticación. Esta petición puede ser mediante usuario contraseña, mediante
              proveedores externos (Google, Facebook, etc)o mediante otros servicios como LDAP,
               Active Directory, etc.
 2. Una vez que el servidor de autenticación garantiza la identidad del cliente,
  se genera un token de acceso
             (JWT).
             3. El cliente usa ese token para acceder a los recursos protegidos que se
              publican mediante API.
             4. En cada petición, el servidor desencripta el token y
             comprueba si el cliente tiene permisos para acceder al
     recurso haciendo una petición al servidor de autorización

Estos token están compuestos por tres partes:
• Header: contiene el hash que se usa para encriptar el token.
• Payload: contiene una serie de atributos (clave, valor) que se encriptan en el token.
• Firma: contiene header y payload concatenados y encriptados (Header + “.” + Payload + Secret key).
     */

    @Value("${secret.key}")
    private String KEY;

    @Value("${expire.length:3600000}")
    private long validityInMilliseconds;

    public String generateToken(UserDetails usuario) {

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setSubject(usuario.getUsername())
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, KEY)
                .compact();
    }

    public boolean validateToken(String token, UserDetails usuario) {
        return usuario
                .getUsername().equals(extractUsername(token)) && !isTokenExpired(token);
    }

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(KEY)
                .parseClaimsJws(token).getBody();
    }

}