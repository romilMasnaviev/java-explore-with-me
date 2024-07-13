package ru.masnaviev.explore.dto.compilation;

import lombok.Data;
import ru.masnaviev.explore.dto.event.EventShortDto;

import java.util.List;

@Data
public class CompilationDto {
    private int id;
    private List<EventShortDto> events;
    private boolean pinned;
    private String title;
}
