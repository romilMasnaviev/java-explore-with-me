package ru.practicum.service.compilation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.practicum.dto.compilation.CompilationDto;
import ru.practicum.dto.compilation.NewCompilationDto;
import ru.practicum.dto.compilation.UpdateCompilationRequest;

public interface AdminCompilationService {
    ResponseEntity<CompilationDto> createCompilationByAdmin(NewCompilationDto compilationDto);

    ResponseEntity<HttpStatus> deleteCompilationByAdmin(Integer compId);

    CompilationDto updateCompilationByAdmin(Integer compId, UpdateCompilationRequest request);
}
