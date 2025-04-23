package edu.tcu.cs.frogcrewonline.gameschedule;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GameScheduleRepository extends JpaRepository<GameSchedule, Integer> {
}
