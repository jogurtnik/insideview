package uk.co.punishell.insideview.model.database.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Setter
@Getter
@Entity
public class Horse extends BaseEntity {

    private String name;

    public Horse() {
    }

    public Horse(String name) {

        this.name = name;
    }
}
