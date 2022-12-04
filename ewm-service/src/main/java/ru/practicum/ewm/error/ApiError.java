package ru.practicum.ewm.error;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class ApiError {
    private List<RuntimeException> errors = new ArrayList<>();
    private String message;
    private String reason;
    private String status;
    private LocalDateTime timestamp;
}
