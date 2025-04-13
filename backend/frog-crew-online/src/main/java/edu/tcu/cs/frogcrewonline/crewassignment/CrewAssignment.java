package edu.tcu.cs.frogcrewonline.crewassignment;

import edu.tcu.cs.frogcrewonline.crewmember.CrewMember;
import edu.tcu.cs.frogcrewonline.game.Game;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class CrewAssignment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Game game;

    @ManyToOne
    private CrewMember crewMember;

    @Column(nullable = false)
    private String position; // e.g., "Camera", "Producer", etc.

    @Column(nullable = false)
    private String reportTime; // You can use LocalTime if preferred

    @Column(nullable = false)
    private String reportLocation;

    public CrewAssignment() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public CrewMember getCrewMember() {
        return crewMember;
    }

    public void setCrewMember(CrewMember crewMember) {
        this.crewMember = crewMember;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public String getReportLocation() {
        return reportLocation;
    }

    public void setReportLocation(String reportLocation) {
        this.reportLocation = reportLocation;
    }
}
