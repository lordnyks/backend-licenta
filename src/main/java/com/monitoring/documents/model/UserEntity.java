package com.monitoring.documents.model;


import com.sun.istack.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;




@Entity
@Table(name = "users")
public class UserEntity implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    private String password;

    @NotNull
    @NotBlank
    @Column(name = "email", unique = true)
    @Size(min = 4)
    private String email;

    @Column(name = "username")
    private String username;

    @OneToOne(targetEntity = Profile.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "Profile", referencedColumnName = "id")
    private Profile profile;


    @Enumerated(EnumType.STRING)
    private ERole role;

    private boolean isEnabled;

    public UserEntity() {

    }

    public UserEntity(Long id, String password, String email, Profile profile) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.profile = profile;

    }

    public UserEntity(String firstName, String lastName, LocalDate dateOfBirth, String email,
                      String username, String password, String gender, String phoneNumber) {
        Profile profile = new Profile();
        Address address = new Address();
        profile.setAddress(address);
        profile.setFirstName(firstName);
        profile.setLastName(lastName);
        profile.setDateOfBirth(dateOfBirth);
        profile.setGender(gender);
        profile.setPhoneNumber(phoneNumber);

        this.profile = profile;
        this.password = password;
        this.email = email;
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(this.role.name()));

        return authorities;
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
        return false;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public ERole getRole() {
        return role;
    }

    public void setRole(ERole role) {
        this.role = role;
    }
}

