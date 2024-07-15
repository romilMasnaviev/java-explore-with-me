package ru.masnaviev.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "locations")
@Data
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "lat")
    private float lat;
    @Column(name = "lon")
    private float lon;
}
