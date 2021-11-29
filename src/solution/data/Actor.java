package solution.data;

import actor.ActorsAwards;
import fileio.ActorInputData;

import java.util.ArrayList;
import java.util.Map;

/**
 * Custom class for Actor data
 */
public final class Actor {
    /**
     * actor name
     */
    private String name;
    /**
     * description of the actor's career
     */
    private String careerDescription;
    /**
     * videos starring actor
     */
    private ArrayList<String> filmography;
    /**
     * awards won by the actor
     */
    private Map<ActorsAwards, Integer> awards;
    /**
     * Actor grade
     */
    private double grade;
    /**
     * Total awards
     */
    private Integer totalAwards;

    public Actor(final ActorInputData actor) {
        this.name = actor.getName();
        this.careerDescription = actor.getCareerDescription();
        this.filmography = actor.getFilmography();
        this.awards = actor.getAwards();
        this.grade = 0.0;
        this.totalAwards = 0;
        for (Map.Entry<ActorsAwards, Integer> entry : this.awards.entrySet()) {
            this.totalAwards += entry.getValue();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public ArrayList<String> getFilmography() {
        return filmography;
    }

    public void setFilmography(final ArrayList<String> filmography) {
        this.filmography = filmography;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    public String getCareerDescription() {
        return careerDescription;
    }

    public void setCareerDescription(final String careerDescription) {
        this.careerDescription = careerDescription;
    }

    public void setAwards(final Map<ActorsAwards, Integer> awards) {
        this.awards = awards;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(final double grade) {
        this.grade = grade;
    }

    public Integer getTotalAwards() {
        return totalAwards;
    }

    public void setTotalAwards(final Integer totalAwards) {
        this.totalAwards = totalAwards;
    }
}
