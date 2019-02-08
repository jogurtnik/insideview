package uk.co.punishell.insideview.model.database.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@Entity
@NamedEntityGraph(name = "graph.Race.runners",
                  attributeNodes = @NamedAttributeNode(value = "runners",
                                                       subgraph = "runners"),
                  subgraphs = @NamedSubgraph(name = "runners",
                                             attributeNodes = @NamedAttributeNode("horse")))
public class Race extends BaseEntity {

    private Date date;
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

    public Race(Date date, String country, String city,
                double trackLength, List<RaceType> raceTypes, LocalTime time) {
        this.date = date;
        this.country = country;
        this.city = city;
        this.trackLength = trackLength;
        this.raceTypes = raceTypes;
        this.time = time;
    }

    public Race(Date date, String country, String city,
                double trackLength, List<RaceType> raceTypes, LocalTime time, List<Runner> runners) {
        this.date = date;
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
        return Double.compare(race.getTrackLength(), getTrackLength()) == 0 &&
                Objects.equals(getDate(), race.getDate()) &&
                Objects.equals(getCountry(), race.getCountry()) &&
                Objects.equals(getCity(), race.getCity()) &&
                Objects.equals(getTime(), race.getTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDate(), getCountry(), getCity(), getTrackLength(), getTime());
    }
}
