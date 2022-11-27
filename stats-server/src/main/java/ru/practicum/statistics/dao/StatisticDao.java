package ru.practicum.statistics.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.statistics.model.Hit;

import java.time.LocalDateTime;

@Repository
public interface StatisticDao extends JpaRepository<Hit, Long> {
    @Query("SELECT count(h) FROM Hit h " +
            "WHERE h.timestamp >= ?1 " +
            "AND h.timestamp <= ?2 " +
            "AND h.uri LIKE ?3")
    Long getStatistics(LocalDateTime start, LocalDateTime end, String uri);

    @Query("SELECT count(h) FROM Hit h " +
            "WHERE h.timestamp >= ?1 " +
            "AND h.timestamp <= ?2 " +
            "AND h.uri LIKE ?3 ORDER BY h.ip")
    Long getUniqueStatistics(LocalDateTime start, LocalDateTime end, String uri);
}
