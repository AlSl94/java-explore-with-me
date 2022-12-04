package ru.practicum.statistics.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.statistics.model.Hit;

import java.time.LocalDateTime;
import java.util.List;

public interface StatisticDao extends JpaRepository<Hit, Long> {

    List<Hit> findAllByTimestampBetweenAndUriIn(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("SELECT COUNT (ip) FROM Hit " +
            "WHERE uri = ?1")
    Integer findHitCountByUri(String uri);

    @Query("SELECT COUNT (DISTINCT ip) FROM Hit " +
            "WHERE uri = ?1")
    Integer findHitCountByUriWithUniqueIp(String uri);
}
