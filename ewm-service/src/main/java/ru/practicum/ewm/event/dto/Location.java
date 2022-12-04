package ru.practicum.ewm.event.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class Location {
    private Double lat;
    private Double lon;
}
