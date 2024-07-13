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


//public class Compilation {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//    @OneToMany(mappedBy = "compilation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Event> events;
//
//    private boolean pinned;
//    private String title;
//}


//public class CompilationDto {
//    private int id;
//    private List<EventShortDto> events;
//    private boolean pinned;
//    private String title;
//}
