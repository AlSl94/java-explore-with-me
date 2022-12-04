package ru.practicum.ewm.compilation.dto;

import lombok.*;
import ru.practicum.ewm.event.dto.EventShortDto;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class CompilationDto {
    private Long id;
    private Set<EventShortDto> events = new HashSet<>();
    private Boolean pinned;
    private String title;
}
