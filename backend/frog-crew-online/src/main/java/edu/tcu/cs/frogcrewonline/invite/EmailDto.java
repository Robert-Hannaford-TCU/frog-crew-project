package edu.tcu.cs.frogcrewonline.invite;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class EmailDto {

    // List of emails -> valid and can't be empty
    @NotEmpty(message = "Email list cannot be empty.")
    private List<@Email(message = "Invalid email format.") String> emails;

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }
}