package uk.co.punishell.insideview.model.database.entities;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@Entity
@Table(name = "RACE")
@NamedEntityGraph(name = "graph.Race.runners",
                  attributeNodes = @NamedAttributeNode(value = "runners",
                                                       subgraph = "runners"),
                  subgraphs = @NamedSubgraph(name = "runners",
                                             attributeNodes = @NamedAttributeNode("horse")))
public class Race extends BaseEntity implements Comparable<Race> {

    private LocalDate localDate;
    private String country;
    private String city;
    private double trackLength;

    @ElementCollection(targetClass = RaceType.class)
    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "raceType_id"))
    private List<RaceType> raceTypes = new ArrayList<>();

    private LocalTime time;

    @OneToMany (cascade = CascadeType.ALL, mappedBy = "race")
    private List<Runner> runners = new ArrayList<>();

    public Race() {
    }

    public Race(LocalDate localDate, String country, String city,
                double trackLength, List<RaceType> raceTypes, LocalTime time) {
        this.localDate = localDate;
        this.country = country;
        this.city = city;
        this.trackLength = trackLength;
        this.raceTypes = raceTypes;
        this.time = time;
    }

    public Race(LocalDate localDate, String country, String city,
                double trackLength, List<RaceType> raceTypes, LocalTime time, List<Runner> runners) {
        this.localDate = localDate;
        this.country = country;
        this.city = city;
        this.trackLength = trackLength;
        this.raceTypes = raceTypes;
        this.time = time;
        this.runners = runners;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Race)) return false;
        Race race = (Race) o;

        if (getLocalDate().isEqual(race.getLocalDate())) {
            if (getTime().equals(race.getTime())) {
                if (getCountry().equalsIgnoreCase(race.getCountry())) {
                    if (getCity().equalsIgnoreCase(race.getCity())) {
                        return true;
                    } else return false;
                } else return false;
            } else return false;
        } else return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLocalDate(), getCountry(), getCity(), getTrackLength(), getTime());
    }

    @Override
    public int compareTo(@NotNull Race anotherRace) {

        if (this == anotherRace) return 0;

        int cmp = this.getLocalDate().compareTo(anotherRace.getLocalDate());

        if (cmp > 0) {
            return 1;
        } else if (cmp < 0) {
            return -1;
        } else if (cmp == 0) {
            return this.getTime().compareTo(anotherRace.getTime());
        }

        return -1;
    }
}
