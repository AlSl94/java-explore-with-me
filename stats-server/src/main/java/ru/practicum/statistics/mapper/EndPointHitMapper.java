package ru.practicum.statistics.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.statistics.dto.EndPointHit;
import ru.practicum.statistics.dto.ViewStats;
import ru.practicum.statistics.model.Hit;
import ru.practicum.statistics.utility.Constants;

import java.time.LocalDateTime;

@Component
public class EndPointHitMapper {

    private EndPointHitMapper() {

    }

    public static Hit toHit(EndPointHit endPointHit) {
        return Hit.builder()
                .id(endPointHit.getId())
                .uri(endPointHit.getUri())
                .app(endPointHit.getApp())
                .ip(endPointHit.getIp())
                .timestamp(LocalDateTime.parse(endPointHit.getTimestamp(), Constants.TIME_FORMATTER))
                .build();
    }

    public static EndPointHit toEndPointHit(Hit hit) {
        return EndPointHit.builder()
                .id(hit.getId())
                .uri(hit.getUri())
                .app(hit.getApp())
                .ip(hit.getIp())
                .timestamp(hit.getTimestamp().format(Constants.TIME_FORMATTER))
                .build();
    }

    public static ViewStats toViewStats(Hit hit) {
        return ViewStats.builder()
                .app(hit.getApp())
                .uri(hit.getUri())
                .hits(hit.getId())
                .build();
    }
}
