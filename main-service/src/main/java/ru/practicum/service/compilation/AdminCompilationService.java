package ru.practicum.service.compilation;

import org.springframework.http.HttpStatus;
import ru.practicum.dto.compilation.CompilationDto;
import ru.practicum.dto.compilation.NewCompilationDto;
import ru.practicum.dto.compilation.UpdateCompilationRequest;

public interface AdminCompilationService {
    CompilationDto createCompilationByAdmin(NewCompilationDto compilationDto);

    HttpStatus deleteCompilationByAdmin(Integer compId);

    CompilationDto updateCompilationByAdmin(Integer compId, UpdateCompilationRequest request);
}
