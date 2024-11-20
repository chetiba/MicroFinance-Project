package tn.esprit.gnbapp.entities;


import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;
@Data
@Builder
@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class user implements Serializable , UserDetails{

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id_user;
        private String lastname;
        private String firstname;
        @Enumerated(EnumType.STRING)
        private typeuser role;
        private long cin;
        private String address;
        private String phonenumber;
        private String email ;
        private String pwd_user ;
        private String job ;
        private boolean status ;
        private LocalDate birthdate ;




    @OneToMany(mappedBy = "user")
    private Set<event> events;

    @OneToMany(mappedBy = "user")
    private Set<post> posts;
    @OneToMany(mappedBy = "user")
    private Set<consultation> consultations;
    @OneToMany( mappedBy="user")
    private Set<account> account;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="user")
    private Set<credit> credits;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return pwd_user;
    }


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
