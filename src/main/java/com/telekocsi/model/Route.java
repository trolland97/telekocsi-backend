package com.telekocsi.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "routes")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "max_passengers")
    private int maxPassengers;

    @Column(name = "_when")
    private Date when;

    @Column(name = "_where")
    private String where;

    @Column(name = "_from")
    private String from;

    @Column(name = "price")
    private int price;

    @ManyToOne
    @JoinTable(name = "drives",
        joinColumns = @JoinColumn(name = "route_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private User driver;

    @ManyToMany
    @JoinTable(name = "travels",
            joinColumns = @JoinColumn(name = "route_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private List<User> passengers;

    public Route() { }

    public Route(int maxPassengers, Date when, String where, String from,
        User driver, List<User> passengers, int price) {
        this.maxPassengers = maxPassengers;
        this.when = when;
        this.where = where;
        this.from = from;
        this.driver = driver;
        this.passengers = passengers;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public User getDriver() {
        return driver;
    }

    public void setDriver(User driver) {
        this.driver = driver;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMaxPassengers() {
        return maxPassengers;
    }

    public void setMaxPassengers(int maxPassengers) {
        this.maxPassengers = maxPassengers;
    }

    public Date getWhen() {
        return when;
    }

    public void setWhen(Date when) {
        this.when = when;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public List<User> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<User> passengers) {
        this.passengers = passengers;
    }
}
