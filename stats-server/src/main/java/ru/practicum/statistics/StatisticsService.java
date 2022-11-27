package ru.practicum.statistics;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.statistics.dao.StatisticDao;
import ru.practicum.statistics.dto.EndPointHit;
import ru.practicum.statistics.dto.ViewStats;
import ru.practicum.statistics.error.ValidateException;
import ru.practicum.statistics.mapper.EndPointHitMapper;
import ru.practicum.statistics.model.Hit;
import ru.practicum.statistics.utility.Constants;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatisticsService {

    private final StatisticDao statDao;

    public void addStats(EndPointHit endPointHit) {
        Hit hit = EndPointHitMapper.toHit(endPointHit);
        statDao.save(hit);
    }

    public List<ViewStats> getStatistics(String start, String end, List<String> uris, Boolean unique) {

        LocalDateTime rangeStart = LocalDateTime.parse(start, Constants.TIME_FORMATTER);
        LocalDateTime rangeEnd = LocalDateTime.parse(start, Constants.TIME_FORMATTER);

        String app = "service";

        List<ViewStats> views = new ArrayList<>();
        ViewStats viewStats = new ViewStats(null, null, 0L);

        if (uris.isEmpty()) {
            throw new ValidateException("Uris for counting stats not passed");
        }

        if (Boolean.TRUE.equals(unique)) {
            for (String uri : uris) {
                viewStats.setUri(uri);
                viewStats.setApp(app);
                viewStats.setHits(statDao.getUniqueStatistics(rangeStart, rangeEnd, uri));
                views.add(viewStats);
            }
        } else {
            for (String uri : uris) {
                viewStats.setUri(uri);
                viewStats.setApp(app);
                viewStats.setHits(statDao.getStatistics(rangeStart, rangeEnd, uri));
                views.add(viewStats);
            }
        }
        return views;
    }
}
