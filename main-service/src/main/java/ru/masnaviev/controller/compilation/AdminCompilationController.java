package ru.masnaviev.controller.compilation;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.masnaviev.dto.compilation.CompilationDto;
import ru.masnaviev.dto.compilation.NewCompilationDto;
import ru.masnaviev.dto.compilation.UpdateCompilationRequest;
import ru.masnaviev.service.CompilationService;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@AllArgsConstructor
@RequestMapping("/admin/compilations")
@Validated
public class AdminCompilationController {

    private CompilationService service;

    @PostMapping
    public ResponseEntity<CompilationDto> createCompilation(@RequestBody @Valid NewCompilationDto compilationDto) {
        return service.createCompilation(compilationDto);
    }

    @DeleteMapping("/{compId}")
    public ResponseEntity<HttpStatus> deleteCompilation(@PathVariable(name = "compId") @Min(value = 0) Integer compId) {
        return service.deleteCompilation(compId);
    }

    @PatchMapping("/{compId}")
    CompilationDto updateCompilation(@PathVariable(name = "compId") @Min(value = 0) Integer compId,
                                     @RequestBody @Valid UpdateCompilationRequest request) {
        return service.updateCompilation(compId, request);
    }

}
