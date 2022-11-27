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
        service.addStats(endpointHit);
    }

    @GetMapping("/stats")
    public List<ViewStats> getStatistics(@RequestParam(name = "start") @NotNull String start,
                                         @RequestParam(name = "end") @NotNull String end,
                                         @RequestParam(name = "uris") List<String> uris,
                                         @RequestParam(name = "unique", defaultValue = "false") Boolean unique) {
        return service.getStatistics(start, end, uris, unique);
    }
}
