package tn.esprit.gnbapp.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.gnbapp.config.JwtService;
import tn.esprit.gnbapp.entities.typeuser;
import tn.esprit.gnbapp.entities.user;
import tn.esprit.gnbapp.repositories.userRepository;
import java.util.Optional;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Service
@RequiredArgsConstructor
public class authServices {

    private final userRepository UR;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;




   public authResponse register(user u) {
       user user = new user();
       user.setFirstname(u.getFirstname());
       user.setLastname(u.getLastname());
       user.setEmail(u.getEmail());
       user.setPwd_user(passwordEncoder.encode(u.getPwd_user()));
       user.setRole(typeuser.client);
       user.setAddress(u.getAddress());
       user.setBirthdate(u.getBirthdate());
       user.setCin(u.getCin());
       user.setJob(u.getJob());
       user.setPhonenumber(u.getPhonenumber());
       user savedUser = UR.save(user);
       String jwtToken = jwtService.generateToken(savedUser);
       String success = "sign up successful!";
       return authResponse.builder().token(jwtToken).welcome(success).build();
   }

    public authResponse authenticate(authRequest request) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPwd_user()
                    )
            );
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid email or password");
        }

        Optional<user> userOptional = UR.findByEmail(request.getEmail());
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        user user = userOptional.get();
        if (!passwordEncoder.matches(request.getPwd_user(), user.getPwd_user())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        String jwtToken = jwtService.generateToken(user);
        String successMessage = "Login successful!";

        return new authResponse(jwtToken, successMessage);
    }
}















