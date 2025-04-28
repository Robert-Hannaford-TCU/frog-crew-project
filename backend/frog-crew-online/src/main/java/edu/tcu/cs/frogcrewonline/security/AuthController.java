package edu.tcu.cs.frogcrewonline.security;

import edu.tcu.cs.frogcrewonline.crewmember.CrewMember;
import edu.tcu.cs.frogcrewonline.crewmember.CrewMemberRepository;
import edu.tcu.cs.frogcrewonline.system.Result;
import edu.tcu.cs.frogcrewonline.system.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/*
    Handles login request
    Uses HTTP Basic Authentication
    Returns userId and role if successful login
 */

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    private final CrewMemberRepository crewMemberRepository;

    public AuthController(CrewMemberRepository crewMemberRepository) {
        this.crewMemberRepository = crewMemberRepository;
    }

    @PostMapping("/login")
    public Result login(Authentication authentication) {
        try {
            String email = authentication.getName();

            CrewMember crewMember = crewMemberRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found."));

            Map<String, Object> data = new HashMap<>();
            data.put("userId", crewMember.getUserId());
            data.put("role", crewMember.getRole());

            return new Result(true, StatusCode.SUCCESS, "Login Success", data);

        } catch (Exception e) {
            return new Result(false, StatusCode.UNAUTHORIZED, "username or password is incorrect", null);
        }
    }
}



