package ru.practicum.ewm.compilation.dto;

import lombok.*;
import ru.practicum.ewm.event.dto.EventShortDto;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class CompilationDto {
    private Long id;
    private List<EventShortDto> events = new ArrayList<>();
    private Boolean pinned;
    private String title;
}
