package ru.practicum.ewm.requests.dto;

import lombok.*;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.event.dto.Location;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class UpdateEventRequest {
    private String annotation;
    private CategoryDto category;
    private String description;
    private LocalDateTime eventDate;
    private Integer eventId;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private String title;
}
