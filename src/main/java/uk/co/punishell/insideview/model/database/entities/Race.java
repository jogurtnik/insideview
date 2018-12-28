package uk.co.punishell.insideview.model.database.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
public class Race extends BaseEntity {

    private Date date;
    private String country;
    private String city;
    private String trackLength;
    private String trackType;
    private LocalTime time;

    @OneToMany (cascade = CascadeType.ALL, mappedBy = "race")
    private Set<Runner> runners = new HashSet<>();

    public Race() {
    }

    public Race(Date date, String country, String city, String trackLength, String trackType, LocalTime time) {
        this.date = date;
        this.country = country;
        this.city = city;
        this.trackLength = trackLength;
        this.trackType = trackType;
        this.time = time;
    }

    public Race(Date date, String country, String city, String trackLength, String trackType, LocalTime time, Set<Runner> runners) {
        this.date = date;
        this.country = country;
        this.city = city;
        this.trackLength = trackLength;
        this.trackType = trackType;
        this.time = time;
        this.runners = runners;
    }
}
