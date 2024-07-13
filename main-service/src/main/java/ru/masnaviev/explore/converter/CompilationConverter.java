package ru.masnaviev.explore.converter;

import org.mapstruct.Mapper;
import ru.masnaviev.explore.dto.compilation.CompilationDto;
import ru.masnaviev.explore.model.Compilation;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompilationConverter {

    CompilationDto compilationConvertToCompilationDto(Compilation compilation);

    List<CompilationDto> compilationConvertToCompilationDto(List<Compilation> compilation);

}