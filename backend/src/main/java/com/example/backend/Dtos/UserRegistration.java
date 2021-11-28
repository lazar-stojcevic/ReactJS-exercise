package com.example.backend.Dtos;

public class UserRegistration {
    private String email;

    private String password;

    private String firstname;

    private String lastname;

    private String street;

    private String city;

    private String country;

    private String userType;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() { return street; }

    public void setAddress(String address) { this.street = address; }

    public String getCountry() { return country; }

    public void setCountry(String country) { this.country = country; }

    public String getCity() { return city; }

    public void setCity(String city) { this.city = city; }

    public String getUserType() { return userType; }

    public void setUserType(String userType) { this.userType = userType; }
}
