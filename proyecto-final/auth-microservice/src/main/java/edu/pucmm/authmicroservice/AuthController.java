package edu.pucmm.authmicroservice;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserClient userClient;

    @PostMapping("/login")
    public String getTokenProvidingUsernameAndPassword(@RequestBody AuthenticationRequest credential) {
        credential.setEmail(credential.getEmail().toLowerCase());
        try {
            UserDto user = userClient.findUserByEmail(credential);
            if (user.getPassword().equals(credential.getPassword())) {
                String secret = "VHKJMNnbfhbsjkdbVJHVkhbJBKJBsmfnbngygiyguFYVHJbkjnjnsjdnlkfn";
                return Jwts.builder()
                        .claim("id", user.getId())
                        .claim("email", user.getEmail())
                        .claim("role", user.getRole())
                        .setSubject(credential.getEmail())
                        .setIssuedAt(Date.from(Instant.now()))
                        .setExpiration(Date.from(Instant.now().plus(1440, ChronoUnit.MINUTES)))
                        .signWith(SignatureAlgorithm.HS256, secret)
                        .compact();
            }
        }catch (ResponseStatusException e){
            if(e.getStatusCode().equals(HttpStatus.NOT_FOUND)){
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email o Clave incorrectos");
            }else{
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrio un error inseperado en el servidor");
            }
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email o Clave incorrectos");
    }
}
