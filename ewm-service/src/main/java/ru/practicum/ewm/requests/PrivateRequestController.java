package ru.practicum.ewm.requests;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.requests.dto.ParticipationRequestDto;

@Slf4j
@Validated
@RestController
@RequestMapping
@RequiredArgsConstructor
public class PrivateRequestController {

    @GetMapping(value = "/users/{userId}/requests")
    ParticipationRequestDto getUserRequests(@PathVariable Long userId) {
        return null;
    }

    @PostMapping(value = "/users/{userId}/requests")
    ParticipationRequestDto addNewRequest(@PathVariable Long userId,
                                          @RequestParam Long eventId) {
        return null;
    }

    @PatchMapping(value = "/users/{userId}/requests/{requestId}/cancel")
        ParticipationRequestDto cancelRequest(@PathVariable Long requestId,
                                              @PathVariable Long userId) {
            return null;
        }
}
