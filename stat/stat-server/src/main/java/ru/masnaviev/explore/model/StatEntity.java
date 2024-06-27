package ru.masnaviev.explore.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Сущность статистики
 */
@Data
@Entity
@Table(name = "stat_entity")
public class StatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "app")
    private String app;
    @Column(name = "uri")
    private String uri;
    @Column(name = "ip")
    private String ip;
    @Column(name = "timestamp")
    private LocalDateTime timestamp;
}
