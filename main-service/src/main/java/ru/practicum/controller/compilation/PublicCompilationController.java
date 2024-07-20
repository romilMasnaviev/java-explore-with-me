package ru.practicum.controller.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.compilation.CompilationDto;
import ru.practicum.model.Compilation;
import ru.practicum.service.compilation.PublicCompilationService;

import java.util.List;

/**
 * Публичный Контроллер для {@link Compilation}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/compilations")
@Validated
public class PublicCompilationController {

    private final PublicCompilationService service;

    /**
     * Получение списка подборок.
     *
     * @param pinned флаг, указывающий, являются ли подборки закрепленными
     * @param from   индекс начала выборки
     * @param size   размер выборки
     * @return список подборок
     */
    @GetMapping
    public List<CompilationDto> getCompilationsPublic(@RequestParam(name = "pinned", required = false) Boolean pinned,
                                                      @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                      @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return service.getCompilationsPublic(pinned, from, size);
    }

    /**
     * Получение подборки по идентификатору.
     *
     * @param compId идентификатор подборки
     * @return информация о подборке
     */
    @GetMapping("/{compId}")
    public ResponseEntity<CompilationDto> getCompilationByIdPublic(@PathVariable Integer compId) {
        return new ResponseEntity<>(service.getCompilationByIdPublic(compId), HttpStatus.OK);
    }
}
