package ru.practicum.statistics;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.statistics.dto.EndPointHit;
import ru.practicum.statistics.dto.ViewStats;

import javax.validation.constraints.NotNull;
import java.util.List;


@RestController
@RequestMapping
@Slf4j
@Validated
@RequiredArgsConstructor
public class StatisticsController {
    private final StatisticsService service;

    @PostMapping("/hit")
    public void addHit(@RequestBody EndPointHit endpointHit) {
        service.addHit(endpointHit);
    }

    @GetMapping("/stats")
    public List<ViewStats> getStatistics(@RequestParam(name = "start") @NotNull String start,
                                         @RequestParam(name = "end") @NotNull String end,
                                         @RequestParam(name = "uris") List<String> uris,
                                         @RequestParam(name = "unique", defaultValue = "false") Boolean unique) {
        List<ViewStats> stats = service.getStatistic(start, end, uris, unique);
        log.info("Got statistic, {}", stats.toString());
        return stats;
    }

    @GetMapping("/views")
    public List<ViewStats> findEventViews(@RequestParam String start, @RequestParam String end,
                                          @RequestParam(required = false) List<String> uris,
                                          @RequestParam(defaultValue = "false") Boolean unique) {
        List<ViewStats> eventViews = service.findEventViews(start, end, uris, unique);
        log.info("Получена статистика по просмотрам");
        return eventViews;
    }

}