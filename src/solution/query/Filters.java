package solution.query;

import actor.ActorsAwards;
import fileio.ActionInputData;
import fileio.ActorInputData;
import solution.data.Show;
import utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class Filters {
    private List<Integer> years;
    private List<String> genres;
    private List<String> words;
    private List<String> awards;

    /**
     * Constants for filters
     */
    public static final int YEARS = 0;
    public static final int GENRES = 1;
    public static final int WORDS = 2;
    public static final int AWARDS = 3;

    public Filters(final ActionInputData command) {

        this.years = new ArrayList<>();
        this.genres = new ArrayList<>();
        this.words = new ArrayList<>();
        this.awards = new ArrayList<>();

        List<List<String>> filters = command.getFilters();
        for (int i = 0; i < filters.size(); i++) {
            List<String> filterList = filters.get(i);

            if (i == YEARS && filterList != null) {
                for (String filter : filterList) {
                    if (filter != null) {
                        this.years.add(Integer.parseInt(filter));
                    }
                }
            }
            if (i == GENRES && filterList != null) {
                for (String filter : filterList) {
                    this.genres.add(filter);
                }
            }
            if (i == WORDS && filterList != null) {
                for (String filter : filterList) {
                    this.words.add(filter);
                }
            }
            if (i == AWARDS && filterList != null) {
                for (String filter : filterList) {
                    this.awards.add(filter);
                }
            }
        }
    }

    public List<Integer> getYears() {
        return years;
    }

    public void setYears(final List<Integer> years) {
        this.years = years;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(final List<String> genres) {
        this.genres = genres;
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(final List<String> words) {
        this.words = words;
    }

    public List<String> getAwards() {
        return awards;
    }

    public void setAwards(final List<String> awards) {
        this.awards = awards;
    }

    /**
     * Check filters for actor
     */
    public boolean checkActorFilters(final ActorInputData actor) {
        String description = actor.getCareerDescription();
        Map<ActorsAwards, Integer> awardsMap = actor.getAwards();

        if (this.words != null) {
            for (String word : this.words) {
                List<String> descriptionRegex = Arrays.asList(description
                        .replaceAll("[^a-zA-Z ]", " ")
                        .toLowerCase()
                        .split("\\s+"));
                String wordLowerCase = word.toLowerCase(Locale.ROOT);
                if (!descriptionRegex.contains(wordLowerCase)) {
                    return false;
                }
            }
        }

        if (this.awards != null) {
            for (String award : this.awards) {
                if (!awardsMap.containsKey(Utils.stringToAwards(award))) {
                    return false;
                }
            }
        }
        return true;
    }
    /**
     * Checks whether show has filters
     */
    public boolean checkShowFilters(final Show show) {
        if (!this.years.isEmpty()) {
            if (!this.years.contains(show.getYear())) {
                return false;
            }
        }
        for (String genre : this.genres) {
            if (genre != null) {
                if (!show.getGenres().contains(genre)) {
                    return false;
                }
            }
        }

        return true;
    }
}
