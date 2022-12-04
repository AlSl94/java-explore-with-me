package ru.practicum.ewm.category.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class NewCategoryDto {
    private String name;
}
