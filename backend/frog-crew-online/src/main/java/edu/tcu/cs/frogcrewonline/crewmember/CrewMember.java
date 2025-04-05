package edu.tcu.cs.frogcrewonline.crewmember;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;
import java.util.List;

@Entity
public class CrewMember  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userId;

    @NotEmpty(message = "First Name is required.")
    private String firstName;

    @NotEmpty(message = "Last Name is required.")
    private String lastName;

    @NotEmpty(message = "Email is required.")
    @Column(unique = true) // CHECK valid email, must be unique
    private String email;

    @NotEmpty(message = "Phone Number is required.")
    //@Pattern(regexp = "\\d{3}-\\d{3}-\\d{4}", message = "Phone number must be in the format 999-999-9999")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private String phoneNumber;

    @NotEmpty(message = "Password is required.")
    private String password;

    @NotEmpty(message = "Role is required.")
    private String role;

    @NotEmpty(message = "Qualified Position required")
    @ElementCollection
    private List<String> qualifiedPosition;

    public CrewMember() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<String> getQualifiedPosition() {
        return qualifiedPosition;
    }

    public void setQualifiedPosition(List<String> qualifiedPosition) {
        this.qualifiedPosition = qualifiedPosition;
    }
}
