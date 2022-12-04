package ru.practicum.ewm.event.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class NewCompilationDto {
    private List<Integer> events = new ArrayList<>();
    private Boolean pinned;
    private String title;
}
