package ru.practicum.dto.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import ru.practicum.dto.event.EventFullDto;
import ru.practicum.dto.event.EventShortDto;
import ru.practicum.dto.event.NewEventDto;
import ru.practicum.model.Event;

import java.util.List;

@Mapper(componentModel = "spring")
@Component("eventConverter")
public interface EventConverter {

    EventFullDto eventConvertToEventFullDto(Event event);

    List<EventFullDto> eventConvertToEventFullDto(List<Event> event);

    @Mapping(target = "category", source = "category", ignore = true)
    Event newEventDtoConvertToEvent(NewEventDto newEvent);

    EventShortDto eventConvertToEventShortDto(Event event);

    List<EventShortDto> eventConvertToEventShortDto(List<Event> event);

}
