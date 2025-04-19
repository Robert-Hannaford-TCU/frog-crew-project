package edu.tcu.cs.frogcrewonline.availability;
import edu.tcu.cs.frogcrewonline.crewmember.CrewMember;
import edu.tcu.cs.frogcrewonline.game.Game;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


import java.io.Serializable;
import java.util.List;

@Entity
public class Availability implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @ManyToOne(optional = false)
    @NotNull(message = "userId is required.")
    @JoinColumn(name = "crew_member_user_id")
    private CrewMember crewMember;

    @ManyToOne(optional = false)
    @NotNull(message = "gameId is required.")
    @JoinColumn(name = "game_game_id")
    private Game game;

    @NotNull(message = "Available is required.")
    private Boolean available;

    private String comment;

    public Availability() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CrewMember getCrewMember() {
        return crewMember;
    }

    public void setCrewMember(CrewMember crewMember) {
        this.crewMember = crewMember;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
