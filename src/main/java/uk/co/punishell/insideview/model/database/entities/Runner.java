package uk.co.punishell.insideview.model.database.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Runner implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Race race;

    @ManyToOne
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

    private int cpr;
    private int nptips;
    private int naps;
    private int stars;

    public Runner() {
    }

    public Runner(Race race, Horse horse, boolean status) {
        this.race = race;
        this.horse = horse;
        this.status = status;
    }

    public Race getRace() { return race; }

    public void setRace(Race race) { this.race = race; }

    public Horse getHorse() { return horse; }

    public void setHorse(Horse horse) {this.horse = horse; }

    public boolean isStatus() { return status; }

    public void setStatus(boolean status) { this.status = status; }

    public double getPrice9() {
        return price9;
    }

    public void setPrice9(double price9) {
        this.price9 = price9;
    }

    public double getPrice10() {
        return price10;
    }

    public void setPrice10(double price10) {
        this.price10 = price10;
    }

    public double getPrice11() {
        return price11;
    }

    public void setPrice11(double price11) {
        this.price11 = price11;
    }

    public double getMov9to11() {
        return mov9to11;
    }

    public void setMov9to11(double mov9to11) {
        this.mov9to11 = mov9to11;
    }

    public double getPrice60() {
        return price60;
    }

    public void setPrice60(double price60) {
        this.price60 = price60;
    }

    public double getMov60() {
        return mov60;
    }

    public void setMov60(double mov60) {
        this.mov60 = mov60;
    }

    public double getPrice30() {
        return price30;
    }

    public void setPrice30(double price30) {
        this.price30 = price30;
    }

    public double getMov30() {
        return mov30;
    }

    public void setMov30(double mov30) {
        this.mov30 = mov30;
    }

    public double getPrice15() {
        return price15;
    }

    public void setPrice15(double price15) {
        this.price15 = price15;
    }

    public double getMov15() {
        return mov15;
    }

    public void setMov15(double mov15) {
        this.mov15 = mov15;
    }

    public double getPrice5() {
        return price5;
    }

    public void setPrice5(double price5) {
        this.price5 = price5;
    }

    public double getMov5() {
        return mov5;
    }

    public void setMov5(double mov5) {
        this.mov5 = mov5;
    }

    public double getPrice3() {
        return price3;
    }

    public void setPrice3(double price3) {
        this.price3 = price3;
    }

    public double getMov3() {
        return mov3;
    }

    public void setMov3(double mov3) {
        this.mov3 = mov3;
    }

    public double getPrice2() {
        return price2;
    }

    public void setPrice2(double price2) {
        this.price2 = price2;
    }

    public double getMov2() {
        return mov2;
    }

    public void setMov2(double mov2) {
        this.mov2 = mov2;
    }

    public double getPrice1() {
        return price1;
    }

    public void setPrice1(double price1) {
        this.price1 = price1;
    }

    public double getMov1() { return mov1; }

    public void setMov1(double mov1) {
        this.mov1 = mov1;
    }

    public double getMean() {
        return mean;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

    public double getMov3to1() {
        return mov3to1;
    }

    public void setMov3to1(double mov3to1) {
        this.mov3to1 = mov3to1;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public boolean isPlaced() {
        return placed;
    }

    public void setPlaced(boolean placed) {
        this.placed = placed;
    }

    public int getCpr() {
        return cpr;
    }

    public void setCpr(int cpr) {
        this.cpr = cpr;
    }

    public int getNptips() {
        return nptips;
    }

    public void setNptips(int nptips) {
        this.nptips = nptips;
    }

    public int getNaps() {
        return naps;
    }

    public void setNaps(int naps) {
        this.naps = naps;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Runner runner = (Runner) o;
        return id != null ? id.equals(runner.id) : runner.id == null;
    }

    @Override
    public int hashCode() {

        return id != null ? id.hashCode() : 0;
    }
}
