package ru.practicum.ewm.requests;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.error.WrongParameterException;
import ru.practicum.ewm.requests.dao.RequestDao;
import ru.practicum.ewm.requests.dto.ParticipationRequestDto;
import ru.practicum.ewm.requests.model.Request;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RequestService {

    private final RequestDao requestDao;


    public List<ParticipationRequestDto> getUserRequests(Long userId) {
        List<Request> requests = requestDao.findAllByRequestorId(userId);
        return RequestMapper.requestDtoList(requests);
    }

    public ParticipationRequestDto addNewRequest(Long userId, Long eventId) {

        Request request = new Request();
        request.setRequestorId(userId);
        request.setEventId(eventId);
        request.setCreated(LocalDateTime.now());
        request.setStatus(RequestStatus.PENDING);

        requestDao.save(request);

        return RequestMapper.requestToDto(request);
    }

    public ParticipationRequestDto cancelRequest(Long requestId, Long userId) {

        Request request = requestDao.findById(requestId)
                .orElseThrow(() -> new WrongParameterException("Запрос с id {" + requestId + "} не найден"));

        if (request.getRequestorId() != userId) {
            throw new WrongParameterException("Пользователь с id {" + userId + "} создателем запроса");
        }

        request.setStatus(RequestStatus.CANCELED);

        requestDao.save(request);

        return RequestMapper.requestToDto(request);
    }
}