package ru.practicum.service.compilation;

import org.springframework.http.ResponseEntity;
import ru.practicum.dto.compilation.CompilationDto;

import java.util.List;

public interface PublicCompilationService {
    List<CompilationDto> getCompilationsPublic(Boolean pinned, Integer from, Integer size);

    ResponseEntity<CompilationDto> getCompilationByIdPublic(Integer compId);
}
