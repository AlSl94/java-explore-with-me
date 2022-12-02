package ru.practicum.ewm.user.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
public class UserDto {
    private Long id;
    private String name;
    private String email;
}
