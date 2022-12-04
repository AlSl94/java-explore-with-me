package ru.practicum.ewm.requests;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.error.ForbiddenException;
import ru.practicum.ewm.error.NotFoundException;
import ru.practicum.ewm.error.WrongParameterException;
import ru.practicum.ewm.event.EventState;
import ru.practicum.ewm.event.dao.EventDao;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.requests.dao.RequestDao;
import ru.practicum.ewm.requests.dto.ParticipationRequestDto;
import ru.practicum.ewm.requests.model.Request;
import ru.practicum.ewm.user.dao.UserDao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RequestService {

    private final RequestDao requestDao;
    private final UserDao userDao;
    private final EventDao eventDao;


    public List<ParticipationRequestDto> findUserRequests(Long userId) {
        List<Request> requests = requestDao.findAllByRequesterId(userId);
        return RequestMapper.requestDtoList(requests);
    }

    @Transactional
    public ParticipationRequestDto addNewRequest(Long userId, Long eventId) {

        Request request = new Request();
        request.setRequester(userDao.findById(userId)
                .orElseThrow(() -> new NotFoundException("Wrong User id")));
        request.setEvent(eventDao.findById(eventId)
                .orElseThrow(() -> new NotFoundException(("Wrong event id"))));
        request.setCreated(LocalDateTime.now());
        request.setStatus(RequestStatus.PENDING);

        requestDao.save(request);

        return RequestMapper.requestToDto(request);

    }

    public List<ParticipationRequestDto> findRequestByUserIdAndEventId(Long userId, Long eventId) {
        List<Request> requests = requestDao.findAllByEventIdAndRequesterId(eventId, ++userId);
        return RequestMapper.requestDtoList(requests);
    }

    @Transactional
    public ParticipationRequestDto cancelRequest(Long reqId, Long userId) {

        Request request = requestDao.findById(reqId)
                .orElseThrow(() -> new NotFoundException("Request " + reqId + " not found"));

        if (!Objects.equals(request.getRequester().getId(), userId)) {
            throw new WrongParameterException("User" + userId + " must be creator");
        }

        request.setStatus(RequestStatus.CANCELED);

        requestDao.save(request);

        return RequestMapper.requestToDto(request);
    }

    @Transactional
    public ParticipationRequestDto confirmRequest(Long userId, Long eventId, Long reqId) {

        Request request = requestDao.findById(reqId)
                .orElseThrow(() -> new WrongParameterException("Request " + reqId + " not found"));

        Event event = eventDao.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event " + eventId + " not found"));

        requestChecker(userId, event);

        if (Objects.equals(event.getConfirmedRequests(), event.getParticipantLimit())) {
            throw new ForbiddenException("Can't exceed participation limit");
        }

        request.setStatus(RequestStatus.CONFIRMED);
        request = requestDao.save(request);

        return RequestMapper.requestToDto(request);
    }

    @Transactional
    public ParticipationRequestDto rejectRequest(Long userId, Long eventId, Long reqId) {

        Request request = requestDao.findById(reqId)
                .orElseThrow(() -> new WrongParameterException("Запрос с id " + reqId + " не найден"));

        Event event = eventDao.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие с id = " + eventId + " не найдено"));

        requestChecker(userId, event);

        request.setStatus(RequestStatus.REJECTED);
        request = requestDao.save(request);

        return RequestMapper.requestToDto(request);
    }

    private void requestChecker(Long userId, Event event) {

        userDao.findById(userId)
                .orElseThrow(() -> new NotFoundException("User " + userId + " not found"));

        if (!event.getInitiator().getId().equals(userId)) {
            throw new ForbiddenException("User must be creator");
        }
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new ForbiddenException("Event is not published");
        }
    }
}
