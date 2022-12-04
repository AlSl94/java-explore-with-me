package ru.practicum.statistics;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.statistics.dao.StatisticDao;
import ru.practicum.statistics.dto.EndPointHit;
import ru.practicum.statistics.dto.ViewStats;
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

    public List<ViewStats> getStatistic(String start, String end, List<String> uris, Boolean unique) {
        List<Hit> hits = statDao.findAllByTimestampBetweenAndUriIn(LocalDateTime
                .parse(start, Constants.TIME_FORMATTER), LocalDateTime.parse(end, Constants.TIME_FORMATTER), uris);
        List<ViewStats> viewStats = new ArrayList<>();

        for (Hit hit : hits) {
            Integer hitCount;
            if (Boolean.TRUE.equals(unique)) {
                hitCount = statDao.findHitCountByUriWithUniqueIp(hit.getUri());
            } else {
                hitCount = statDao.findHitCountByUri(hit.getUri());
            }
            viewStats.add(new ViewStats(hit.getApp(), hit.getUri(), hitCount));
        }
        return viewStats;
    }

    public void addHit(EndPointHit endpointHit) {
        statDao.save(EndPointHitMapper.toHit(endpointHit));
    }

    public List<ViewStats> findEventViews(String start, String end, List<String> uris, Boolean unique) {
        return getStatistic(start, end, uris, unique);
    }
}
