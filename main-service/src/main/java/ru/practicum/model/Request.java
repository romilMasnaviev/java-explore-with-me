package ru.practicum.model;

import lombok.Data;
import ru.practicum.model.enums.Status;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "requests")
@Data
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "event_id")
    private int eventId;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "created")
    private LocalDateTime created;
}