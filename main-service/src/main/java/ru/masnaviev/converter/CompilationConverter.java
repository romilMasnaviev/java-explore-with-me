package ru.masnaviev.converter;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.masnaviev.dto.compilation.CompilationDto;
import ru.masnaviev.model.Compilation;

import java.util.List;

@Mapper(componentModel = "spring")
@Component("compilationConverter")
public interface CompilationConverter {

    CompilationDto compilationConvertToCompilationDto(Compilation compilation);

    List<CompilationDto> compilationConvertToCompilationDto(List<Compilation> compilation);

}