package ru.practicum.ewm.compilation.model;

import lombok.*;
import ru.practicum.ewm.event.model.Event;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "compilations")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private Boolean pinned;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "events_compilations",
            joinColumns = @JoinColumn(name = "compilation_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    @ToString.Exclude
    private Set<Event> events;
}