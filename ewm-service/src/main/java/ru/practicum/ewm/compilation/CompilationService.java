package ru.practicum.ewm.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.compilation.dao.CompilationDao;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.error.NotFoundException;
import ru.practicum.ewm.error.ValidationException;
import ru.practicum.ewm.event.dao.EventDao;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.utility.FromSizeRequest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CompilationService {

    private final CompilationDao compilationDao;
    private final EventDao eventDao;


    public List<CompilationDto> findCompilations(Boolean pinned, int from, int size) {
        Pageable pageable = FromSizeRequest.of(from, size);
        List<Compilation> compilations = compilationDao.findByPinned(pinned, pageable).toList();
        return CompilationMapper.toCompDtoList(compilations);
    }

    public CompilationDto getCompilationById(Long compId) {
        Compilation compilation = compilationDao.findById(compId)
                .orElseThrow(() -> new NotFoundException("Compilation with id = " + compId + " not found"));
        return CompilationMapper.toCompilationDto(compilation);
    }

    @Transactional
    public CompilationDto createCompilation(NewCompilationDto dto) {

        if (dto.getTitle() == null) {
            throw new ValidationException("Wrong body");
        }

        Compilation compilation = CompilationMapper.toCompilation(dto);
        Set<Event> events = new HashSet<>(eventDao.findAllById(dto.getEvents()));
        compilation.setEvents(events);
        compilation = compilationDao.save(compilation);
        return CompilationMapper.toCompilationDto(compilation);
    }

    @Transactional
    public void deleteCompilation(Long compId) {
        compValidation(compId);
        compilationDao.deleteById(compId);
    }

    @Transactional
    public void deleteEventFromCompilation(Long compId, Long eventId) {
        compValidation(compId);
        eventValidation(eventId);
        Compilation compilation = compilationDao.findById(compId)
                .orElseThrow(() -> new NotFoundException("Compilation with id = " + compId + " not found"));
        Set<Event> events = compilation.getEvents();
        Event event = eventDao.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event with id = " + eventId + " not found"));

        if (events.contains(event)) {
            events.remove(event);
            compilation.setEvents(events);
            compilationDao.save(compilation);
        }

    }

    @Transactional
    public void addEventToCompilation(Long compId, Long eventId) {
        compValidation(compId);
        eventValidation(eventId);
        Compilation compilation = compilationDao.findById(compId).get();
        Set<Event> events = compilation.getEvents();
        Event event = eventDao.findById(eventId).get();

        if (!events.contains(event)) {
            events.add(event);
            compilation.setEvents(events);
            compilationDao.save(compilation);
        }

    }

    @Transactional
    public void unpinCompilation(Long compId) {
        compValidation(compId);
        Compilation compilation = compilationDao.findById(compId).get();
        if (compilation.getPinned()) {
            compilation.setPinned(false);
            compilationDao.save(compilation);
        }
    }

    @Transactional
    public void pinCompilation(Long compId) {
        compValidation(compId);
        Compilation compilation = compilationDao.findById(compId).get();
        if (!compilation.getPinned()) {
            compilation.setPinned(true);
            compilationDao.save(compilation);
        }
    }

    private void eventValidation(Long id) {
        if (!eventDao.existsById(id)) {
            throw new NotFoundException("Event with id = " + id + " not found");
        }
    }

    private void compValidation(Long id) {
        if (!compilationDao.existsById(id)) {
            throw new NotFoundException("Compilation with id = " + id + " not found");
        }
    }
}
