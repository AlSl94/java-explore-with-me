package ru.practicum.ewm.category.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class CategoryDto {
    private Long id;
    private String name;
}
