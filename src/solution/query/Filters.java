package solution.query;

import actor.ActorsAwards;
import fileio.ActionInputData;
import fileio.ActorInputData;
import solution.Movie;
import solution.Show;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Filters {
    private List<Integer> years;
    private List<String> genres;
    private List<String> words;
    private List<String> awards;

    public Filters(ActionInputData command) {

        this.years = new ArrayList<>();
        this.genres = new ArrayList<>();
        this.words = new ArrayList<>();
        this.awards = new ArrayList<>();

        List<List<String>> filters = command.getFilters();
        for (int i = 0; i < filters.size(); i++) {
            List<String> filterList = filters.get(i);

            if (i == 0 && filterList != null) {
                for (String filter : filterList) {
                    if (filter != null) {
                        this.years.add(Integer.parseInt(filter));
                    }
                }
            }
            if (i == 1 && filterList != null) {
                for (String filter : filterList) {
                    this.genres.add(filter);
                }
            }
            if (i == 2 && filterList != null) {
                for (String filter : filterList) {
                    this.words.add(filter);
                }
            }
            if (i == 3 && filterList != null) {
                for (String filter : filterList) {
                    this.awards.add(filter);
                }
            }
        }
    }

    public List<Integer> getYears() {
        return years;
    }

    public void setYears(List<Integer> years) {
        this.years = years;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }

    public List<String> getAwards() {
        return awards;
    }

    public void setAwards(List<String> awards) {
        this.awards = awards;
    }

    public boolean checkActorFilters(ActorInputData actor) {
        String description = actor.getCareerDescription();
        Map<ActorsAwards, Integer> awards = actor.getAwards();

        if (this.words != null) {
            for (String word : this.words) {
                if (!description.contains(word)) {
                    return false;
                }
            }
        }

        if (this.awards != null) {
            for (String award : this.awards) {
                if (!awards.containsKey(award)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkShowFilters(Show show) {
        if (!this.years.contains(show.getYear())) {
            return false;
        }
        for (String genre : this.genres) {
            if (!show.getGenres().contains(genre)) {
                return false;
            }
        }

        return true;
    }
}
