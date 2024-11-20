package tn.esprit.gnbapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import tn.esprit.gnbapp.entities.user;
import tn.esprit.gnbapp.services.IuserServices;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class userRestController {

    private final IuserServices userServices;
//crud simple
    @PostMapping("/add")
    user adduser (@RequestBody user user) { return userServices.addOrUpdateuser (user);
    }
    @PutMapping("/update")
    user updateuser (@RequestBody user user) {
        return userServices.addOrUpdateuser (user);
    }
    @GetMapping("/all")
    List<user> getAllusers() { return userServices.retrieveAllusers(); }
    @DeleteMapping("/delete/{id}")
    void deleteuser (@PathVariable("id") int id_user) {
        userServices.removeuser (id_user);
    }
    //metiers avancés
    @GetMapping("/get/{id}")
    user getuser (@PathVariable("id") int id_user) {
        return userServices.retrieveuser(id_user);
    }
    @PutMapping("/changePassword/{id}/{newPass}")
    public ResponseEntity<String> changePassword(@PathVariable("id") int id_user, @PathVariable("newPass") String newP) throws IOException, MessagingException {
        userServices.changePassword(userServices.retrieveuser(id_user),newP);
        File file = ResourceUtils.getFile("src/main/java/tn/esprit/gnbapp/utils/forget-password-email.html");
        System.out.println("File Found : " + file.exists());
        String content = new String(Files.readAllBytes(file.toPath()));
        userServices.sendmail(getuser(id_user).getEmail(),"goliath bank",content);
        return ResponseEntity.ok("Mail sent successfully!");
    }
    @GetMapping("/verifyUserPassword/{id}/{pass}")
    public ResponseEntity<String> verifyUserPassword(@PathVariable("id") int id_user, @PathVariable("pass") String pwd_user) {
        if(userServices.veriyUserPassword(userServices.retrieveuser(id_user), pwd_user)) {
            return ResponseEntity.ok("The password is correct based on this UserID : There is no need to change it !");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password.");
        }
    }
    @GetMapping("/countUsers")
    public ResponseEntity<Integer> countUsers() {
        int count = userServices.countUsers();
        return ResponseEntity.ok(count);
    }
}
