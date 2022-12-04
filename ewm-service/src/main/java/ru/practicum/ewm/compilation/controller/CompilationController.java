package ru.practicum.ewm.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.CompilationService;
import ru.practicum.ewm.compilation.dto.CompilationDto;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping(path = "/compilations")
@RequiredArgsConstructor
public class CompilationController {

    private final CompilationService compilationService;

    @GetMapping
    List<CompilationDto> findCompilations(@RequestParam(required = false) Boolean pinned,
                                          @RequestParam(required = false, defaultValue = "0") int from,
                                          @RequestParam(required = false, defaultValue = "10") int size) {
        List<CompilationDto> compilations = compilationService.findCompilations(pinned, from, size);
        log.info("Got compilation list, {}", compilations);
        return compilations;
    }

    @GetMapping(value = "/{compId}")
    CompilationDto getCompilationById(@PathVariable Long compId) {
        CompilationDto dto = compilationService.getCompilationById(compId);
        log.info("Got compilation {}", dto);
        return dto;
    }
}
