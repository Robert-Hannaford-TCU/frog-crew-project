package edu.tcu.cs.frogcrewonline.crewmember;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Member;

@Repository
public interface CrewMemberRepository extends JpaRepository<CrewMember, Integer> {
}