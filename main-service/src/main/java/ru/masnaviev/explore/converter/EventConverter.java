package ru.masnaviev.explore.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.masnaviev.explore.dto.event.EventFullDto;
import ru.masnaviev.explore.dto.event.EventShortDto;
import ru.masnaviev.explore.dto.event.NewEventDto;
import ru.masnaviev.explore.model.Event;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventConverter {

    EventFullDto eventConvertToEventFullDto(Event event);

    List<EventFullDto> eventConvertToEventFullDto(List<Event> event);

    @Mapping(target = "category", source = "category", ignore = true)
    Event newEventDtoConvertToEvent(NewEventDto newEvent);

    EventShortDto eventConvertToEventShortDto(Event event);

    List<EventShortDto> eventConvertToEventShortDto(List<Event> event);

}
