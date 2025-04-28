package edu.tcu.cs.frogcrewonline.crewmember;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Member;
import java.util.Optional;

@Repository
public interface CrewMemberRepository extends JpaRepository<CrewMember, Integer> {
    boolean existsByEmail(String email);
    Optional<CrewMember> findByEmail(String email);
}