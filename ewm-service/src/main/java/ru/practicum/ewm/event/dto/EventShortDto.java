package ru.practicum.ewm.event.dto;

import lombok.*;
import ru.practicum.ewm.user.dto.UserShortDto;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class EventShortDto {
    private String annotation;
    private Long category;
    private Integer confirmedRequests;
    private LocalDateTime eventDate;
    private Long id;
    private UserShortDto initiator;
    private Boolean paid;
    private String title;
    private Integer views;
}
