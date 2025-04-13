package edu.tcu.cs.frogcrewonline.crewassignment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrewAssignmentRespository extends JpaRepository<CrewAssignment, Integer> {
    List<CrewAssignment> findByGame_GameId(Integer gameId);

}
