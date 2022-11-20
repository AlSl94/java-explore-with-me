package ru.practicum.ewm.requests;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.requests.dto.ParticipationRequestDto;
import ru.practicum.ewm.requests.model.Request;
import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.user.model.User;

import java.util.ArrayList;
import java.util.List;

@Component
public class RequestMapper {

    private RequestMapper() {
    }

    public static ParticipationRequestDto requestToDto(Request request) {
        return ParticipationRequestDto.builder()
                .created(request.getCreated())
                .event(request.getEventId())
                .id(request.getId())
                .requester(request.getRequestorId())
                .status(request.getStatus())
                .build();
    }

    public static Request dtoToRequest(ParticipationRequestDto requestDto) {
        return Request.builder()
                .created(requestDto.getCreated())
                .eventId(requestDto.getEvent())
                .id(requestDto.getId())
                .requestorId(requestDto.getRequester())
                .status(requestDto.getStatus())
                .build();
    }

    public static List<ParticipationRequestDto> requestDtoList(Iterable<Request> requests) {
        List<ParticipationRequestDto> result = new ArrayList<>();
        for (Request request : requests) {
            result.add(requestToDto(request));
        }
        return result;
    }

}
