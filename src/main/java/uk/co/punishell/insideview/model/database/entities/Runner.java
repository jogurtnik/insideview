package uk.co.punishell.insideview.model.database.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Setter
@Getter
@Entity
public class Runner extends BaseEntity implements Comparable<Runner>{

    @ManyToOne
    @JoinColumn(name = "race_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Race race;

    @ManyToOne
    @JoinColumn(name = "horse_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Horse horse;

    private boolean status;

    private double price9;
    private double price10;
    private double price11;
    private double mov9to11;
    private double price60;
    private double mov60;
    private double price30;
    private double mov30;
    private double price15;
    private double mov15;
    private double price5;
    private double mov5;
    private double price3;
    private double mov3;
    private double price2;
    private double mov2;
    private double price1;
    private double mov1;
    private double mean;
    private double mov3to1;

    private boolean winner;
    private boolean placed;

    private int favouritePosition;

    private int cpr;
    private int nptips;
    private int stars;
    private int naps;

    public Runner() {
    }

    public Runner(Race race, Horse horse, boolean status) {
        this.race = race;
        this.horse = horse;
        this.status = status;
    }

    public Runner(Horse horse, boolean status, double price9, double price10, double price11,
                  double mov9to11, double price60, double mov60, double price30, double mov30,
                  double price15, double mov15, double price5, double mov5, double price3, double mov3,
                  double price2, double mov2, double price1, double mov1, double mean, double mov3to1,
                  boolean winner, boolean placed, int cpr, int nptips, int stars, int naps) {
        this.horse = horse;
        this.status = status;
        this.price9 = price9;
        this.price10 = price10;
        this.price11 = price11;
        this.mov9to11 = mov9to11;
        this.price60 = price60;
        this.mov60 = mov60;
        this.price30 = price30;
        this.mov30 = mov30;
        this.price15 = price15;
        this.mov15 = mov15;
        this.price5 = price5;
        this.mov5 = mov5;
        this.price3 = price3;
        this.mov3 = mov3;
        this.price2 = price2;
        this.mov2 = mov2;
        this.price1 = price1;
        this.mov1 = mov1;
        this.mean = mean;
        this.mov3to1 = mov3to1;
        this.winner = winner;
        this.placed = placed;
        this.cpr = cpr;
        this.nptips = nptips;
        this.stars = stars;
        this.naps = naps;
    }

    @Override
    public int compareTo(@NotNull Runner anotherRunner) {
        return Double.compare(this.getPrice1(), anotherRunner.getPrice1());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Runner)) return false;
        Runner runner = (Runner) o;
        return Double.compare(runner.getPrice1(), getPrice1()) == 0 &&
                getHorse().getName().equalsIgnoreCase(runner.getHorse().getName()) &&
                getRace().equals(runner.getRace());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRace(), getHorse(), getPrice1());
    }
}
