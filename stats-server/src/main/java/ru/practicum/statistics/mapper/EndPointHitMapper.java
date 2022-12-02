package ru.practicum.statistics.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.statistics.dto.EndPointHit;
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

}
