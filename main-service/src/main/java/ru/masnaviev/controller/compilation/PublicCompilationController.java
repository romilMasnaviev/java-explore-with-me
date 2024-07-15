package ru.masnaviev.controller.compilation;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.masnaviev.dto.compilation.CompilationDto;
import ru.masnaviev.service.CompilationService;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/compilations")
@Validated
public class PublicCompilationController {

    private CompilationService service;

    @GetMapping()
    public List<CompilationDto> getCompilations(@RequestParam(name = "pinned", required = false) Boolean pinned,
                                                @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return service.getCompilations(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public ResponseEntity<CompilationDto> getCompilationById(@PathVariable(name = "compId") @Min(value = 0) Integer compId) {
        return service.getCompilationById(compId);
    }
}
