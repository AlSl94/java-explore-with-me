package ru.practicum.ewm.category.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@ToString
public class CategoryDto {
    private Long id;
    private String name;
}
