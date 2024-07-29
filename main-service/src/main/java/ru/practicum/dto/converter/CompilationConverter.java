package ru.practicum.dto.converter;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.practicum.dto.compilation.CompilationDto;
import ru.practicum.model.Compilation;

import java.util.List;

@Mapper(componentModel = "spring")
@Component("compilationConverter")
public interface CompilationConverter {

    CompilationDto compilationConvertToCompilationDto(Compilation compilation);

    List<CompilationDto> compilationConvertToCompilationDto(List<Compilation> compilation);

}