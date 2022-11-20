package ru.practicum.ewm.requests.model;

import lombok.*;
import ru.practicum.ewm.requests.RequestStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "requests")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private LocalDateTime created;
    @Column
    private Long eventId;
    @Column
    private Long requestorId;
    @Column
    private RequestStatus status;
}