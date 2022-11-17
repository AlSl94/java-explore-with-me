package ru.practicum.ewm.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.dto.CompilationDto;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping(path = "/compilations") // TODO
@RequiredArgsConstructor
public class CompilationController {

    @GetMapping
    List<CompilationDto> getCompilations(@RequestParam(required = false) Boolean pinned,
                                         @RequestParam(required = false, defaultValue = "0") int from,
                                         @RequestParam(required = false, defaultValue = "10") int size) {
        return null;
    }

    @GetMapping(value = "/{compId}")
    CompilationDto getCompilationById(@PathVariable Long compId) {
        return null;
    }
}
