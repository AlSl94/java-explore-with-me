package ru.practicum.ewm.event.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String annotation;
    @Column
    private String description;
    @Column
    private Long categoryId;
    @Column
    private String title;
    @Column
    private LocalDateTime publishedOn;
    @Column
    private LocalDateTime eventDate;
    @Column
    private Long initiatorId;
    @Column
    private Double lat;
    @Column
    private Double lon;
    @Column
    private Boolean paid;
    @Column
    private Integer participantLimit;
    @Column
    private Boolean requestModeration;
    @Column
    private String state;
}