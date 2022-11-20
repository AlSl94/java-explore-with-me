package ru.practicum.ewm.requests;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.error.WrongParameterException;
import ru.practicum.ewm.event.dao.EventDao;
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


    public List<ParticipationRequestDto> getUserRequests(Long userId) {
        List<Request> requests = requestDao.findAllByRequesterId(userId);
        return RequestMapper.requestDtoList(requests);
    }

    @Transactional
    public ParticipationRequestDto addNewRequest(Long userId, Long eventId) {

//        Request request = new Request();
//        request.setRequester(userDao.findById(userId).orElseThrow(() -> new WrongParameterException("Wrong User id")));
//        request.setEvent(eventDao.findById(eventId).orElseThrow(() -> new WrongParameterException(("Wrong event id"))));
//        request.setCreated(LocalDateTime.now());
//        request.setStatus(RequestStatus.PENDING);
//
//        requestDao.save(request);
//
//        return RequestMapper.requestToDto(request);

        return null;
    }

    @Transactional
    public ParticipationRequestDto cancelRequest(Long reqId, Long userId) {

        Request request = requestDao.findById(reqId)
                .orElseThrow(() -> new WrongParameterException("Запрос с id " + reqId + " не найден"));

        if (!Objects.equals(request.getRequester().getId(), userId)) {
            throw new WrongParameterException("Пользователь с id " + userId + " создателем запроса");
        }

        request.setStatus(RequestStatus.CANCELED);

        requestDao.save(request);

        return RequestMapper.requestToDto(request);
    }

    public ParticipationRequestDto getRequestInfoByUserIdAndEventId(Long userId, Long eventId) {
        Request request = requestDao.findByEventIdAndRequesterId(eventId, userId);
        return RequestMapper.requestToDto(request);
    }

    @Transactional
    public ParticipationRequestDto confirmParticipationRequest(Long userId, Long eventId, Long reqId) { //todo validation
        Request request = requestDao.findByEventIdAndRequesterIdAndId(eventId, userId, reqId);
        request.setStatus(RequestStatus.CONFIRMED);
        request = requestDao.save(request);
        return RequestMapper.requestToDto(request);
    }

    @Transactional
    public ParticipationRequestDto rejectParticipationRequest(Long userId, Long eventId, Long reqId) { // todo
        Request request = requestDao.findByEventIdAndRequesterIdAndId(eventId, userId, reqId);
        request.setStatus(RequestStatus.REJECTED);
        request = requestDao.save(request);
        return RequestMapper.requestToDto(request);
    }
}
