package ru.practicum.controller.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.compilation.CompilationDto;
import ru.practicum.dto.compilation.NewCompilationDto;
import ru.practicum.dto.compilation.UpdateCompilationRequest;
import ru.practicum.model.Compilation;
import ru.practicum.service.compilation.AdminCompilationService;

import javax.validation.Valid;

/**
 * Админ Контроллер для {@link Compilation}
 */
@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
@Validated
public class AdminCompilationController {

    private final AdminCompilationService service;

    /**
     * Создание новой подборки.
     *
     * @param compilationDto данные новой подборки
     * @return созданная подборка
     */
    @PostMapping
    public ResponseEntity<CompilationDto> createCompilationByAdmin(@RequestBody @Valid NewCompilationDto compilationDto) {
        return new ResponseEntity<>(service.createCompilationByAdmin(compilationDto), HttpStatus.CREATED);
    }

    /**
     * Удаление подборки.
     *
     * @param compId идентификатор подборки
     */
    @DeleteMapping("/{compId}")
    public ResponseEntity<HttpStatus> deleteCompilationByAdmin(@PathVariable Integer compId) {
        return new ResponseEntity<>(service.deleteCompilationByAdmin(compId));
    }

    /**
     * Редактирование подборки.
     *
     * @param compId  идентификатор подборки
     * @param request данные для обновления подборки
     * @return обновленная подборка
     */
    @PatchMapping("/{compId}")
    CompilationDto updateCompilationByAdmin(@PathVariable Integer compId,
                                            @RequestBody @Valid UpdateCompilationRequest request) {
        return service.updateCompilationByAdmin(compId, request);
    }
}
