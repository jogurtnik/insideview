package uk.co.punishell.insideview.model.database.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "HORSE")
public class Horse extends BaseEntity {

    private String name;

    @OneToMany(mappedBy = "horse", fetch = FetchType.LAZY)
    private Set<Runner> runners = new HashSet<>();

    public Horse() {
    }

    public Horse(String name) {

        this.name = name;
    }
}
