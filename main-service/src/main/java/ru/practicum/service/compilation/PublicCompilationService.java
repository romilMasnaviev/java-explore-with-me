package ru.practicum.service.compilation;

import ru.practicum.dto.compilation.CompilationDto;

import java.util.List;

public interface PublicCompilationService {
    List<CompilationDto> getCompilationsPublic(Boolean pinned, Integer from, Integer size);

    CompilationDto getCompilationByIdPublic(Integer compId);
}
