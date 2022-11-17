package ru.practicum.ewm.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.dto.CompilationDto;

@Slf4j
@Validated
@RestController
@RequestMapping(path = "/admin/compilations") // TODO
@RequiredArgsConstructor
public class CompilationsAdminController {

    @PostMapping
    CompilationDto createCompilation(@RequestBody CompilationDto compilation) {
        return null;
    }

    @DeleteMapping(value = "/{compId}")
    void deleteCompilation(@PathVariable Long compId) {

    }

    @DeleteMapping(value = "/{compId}/events/{eventId}")
    void deleteEventFromCompilation(@PathVariable Long compId,
                                    @PathVariable Long eventId) {

    }

    @PatchMapping(value = "/{compId}/events/{eventId}")
    void addEventToCompilation(@PathVariable Long compId, @PathVariable Long eventId) {

    }

    @DeleteMapping(value = "/{compId}/pin")
    void unpinCompilation(@PathVariable Long compId) {

    }

    @PatchMapping(value = "/{compId}/pin")
    void pinCompilation(@PathVariable Long compId) {

    }
}
