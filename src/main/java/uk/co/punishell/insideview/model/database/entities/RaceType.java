package uk.co.punishell.insideview.model.database.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Setter
@Getter
@Embeddable
public class RaceType {

    private String name;

    @Enumerated(EnumType.STRING)
    private RaceTypeGroup raceTypeGroup;

    public RaceType() {}

    public RaceType(String name) {
        this.name = name;
    }

    public RaceType(String name, RaceTypeGroup raceTypeGroupp) {
        this.name = name;
        this.raceTypeGroup = raceTypeGroup;
    }
}
