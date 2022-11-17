package ru.practicum.ewm.user.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class UserShortDto {
    private Long id;
    private String name;
}
