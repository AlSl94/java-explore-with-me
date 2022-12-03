package ru.practicum.ewm.compilation;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.event.EventMapper;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class CompilationMapper {

    public static CompilationDto toCompilationDto(Compilation comp) {
        return CompilationDto.builder()
                .id(comp.getId())
                .title(comp.getTitle())
                .pinned(comp.getPinned())
                .events(EventMapper.toShortEventDtoSet(comp.getEvents()))
                .build();
    }

    public static Compilation toCompilation(NewCompilationDto dto) {
        return Compilation.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .pinned(dto.getPinned())
                .build();
    }


    public static List<CompilationDto> toCompDtoList(Iterable<Compilation> compilations) {
        List<CompilationDto> result = new ArrayList<>();
        for (Compilation comp : compilations) {
            result.add(toCompilationDto(comp));
        }
        return result;
    }
}
