package tn.esprit.gnbapp.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.gnbapp.entities.user;
import javax.validation.Valid;




@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class authController {
    private final authServices service;
    @PostMapping("/register")
    public ResponseEntity<authResponse> register(
            @RequestBody user u) {
return ResponseEntity.ok(service.register(u));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@Valid @RequestBody authRequest request) {
        try {
            authResponse authResponse = service.authenticate(request);
            return ResponseEntity.ok("Login successful. Token generated: " + authResponse.getToken());
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect email or password");
        }
    }
}
