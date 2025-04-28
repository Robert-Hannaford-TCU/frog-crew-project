package edu.tcu.cs.frogcrewonline.security;

import edu.tcu.cs.frogcrewonline.crewmember.CrewMember;
import edu.tcu.cs.frogcrewonline.crewmember.CrewMemberRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*
    Loads specific data for authentication by email
 */

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final CrewMemberRepository crewMemberRepository;

    public CustomUserDetailsService(CrewMemberRepository crewMemberRepository) {
        this.crewMemberRepository = crewMemberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        CrewMember crewMember = this.crewMemberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

        return User.builder()
                .username(crewMember.getEmail())
                .password(crewMember.getPassword())
                .roles(crewMember.getRole())
                .build();
    }
}