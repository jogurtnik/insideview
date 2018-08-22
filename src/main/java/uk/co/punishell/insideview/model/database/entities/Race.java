package uk.co.punishell.insideview.model.database.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;
import java.util.Set;

@Entity
public class Race implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date date;
    private String country;
    private String city;
    private String TrackLength;
    private String TrackType;
    private LocalTime time;

    @OneToMany (mappedBy = "race")
    private Set<Runner> runners;

    public Race() {
    }

    public Race(Date date, String country, String city, String trackLength, String trackType, LocalTime time) {
        this.date = date;
        this.country = country;
        this.city = city;
        TrackLength = trackLength;
        TrackType = trackType;
        this.time = time;
    }

    public Race(Date date, String country, String city, String trackLength, String trackType, LocalTime time, Set<Runner> runners) {
        this.date = date;
        this.country = country;
        this.city = city;
        TrackLength = trackLength;
        TrackType = trackType;
        this.time = time;
        this.runners = runners;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId(){
        return id;
    }

    public void setRunners(Set<Runner> runners) {
        this.runners = runners;
    }

    public Set<Runner> getRunners() {
        return runners;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTrackLength() {
        return TrackLength;
    }

    public void setTrackLength(String trackLength) {
        TrackLength = trackLength;
    }

    public String getTrackType() {
        return TrackType;
    }

    public void setTrackType(String trackType) {
        TrackType = trackType;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Race race = (Race) o;
        return id != null ? id.equals(race.id) : race.id == null;
    }

    @Override
    public int hashCode() {

        return id != null ? id.hashCode() : 0;
    }
}
