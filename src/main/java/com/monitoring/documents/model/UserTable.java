package com.monitoring.documents.model;


import javax.persistence.*;
import java.util.List;


/* Modelul tabelului User;
 * UserId
 * Password
 * Email -> username
 * Profil(Numar telefon)
 * Cars(Numar masina)
 * Role
 */

@Entity
@Table
public class UserTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String password;
    private String email;
    private Profile profile;
    private List<Car> cars;
    private String role;


    public UserTable() {

    }


    public UserTable(Long id, String password, String email, Profile profile, List<Car> cars, String role) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.profile = profile;
        this.cars = cars;
        this.role = role;
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

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

