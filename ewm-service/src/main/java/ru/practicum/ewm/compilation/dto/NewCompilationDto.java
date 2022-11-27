package ru.practicum.ewm.compilation.dto;

import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class NewCompilationDto {
    private Long id;
    private Set<Long> events;
    private Boolean pinned;
    private String title;
}