package edu.tcu.cs.frogcrewonline.invite;

import edu.tcu.cs.frogcrewonline.crewmember.CrewMemberRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// handles logic of sending invitations

@Service
public class InviteService {

    private final CrewMemberRepository crewMemberRepository;
    private final EmailService emailService;

    public InviteService(CrewMemberRepository crewMemberRepository, EmailService emailService) {
        this.crewMemberRepository = crewMemberRepository;
        this.emailService = emailService;
    }

    public List<String> sendInvites(List<String> emails) {
        List<String> sent = new ArrayList<>();

        for (String email : emails) {

            // only send invite if email not already in system
            if (!crewMemberRepository.existsByEmail(email)) {
                String token = UUID.randomUUID().toString();
                String link = "https://localhost:8080/register?token=" + token;

                String body = "You've been invited to join Frog Crew!\nClick to register: " + link;

                emailService.sendEmail(email, "Frog Crew Invitation", body);
                // add to sent emails
                sent.add(email);
            }
        }

        return sent;
    }
}