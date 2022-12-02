package ru.practicum.ewm.client.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ViewStats {

    @NotBlank
    private String app;
    @NotBlank
    private String uri;
    @NotBlank
    private Long hits;
}
