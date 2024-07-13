package ru.masnaviev.explore.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.masnaviev.explore.converter.CompilationConverter;
import ru.masnaviev.explore.dao.CompilationRepository;
import ru.masnaviev.explore.dao.EventRepository;
import ru.masnaviev.explore.dto.compilation.CompilationDto;
import ru.masnaviev.explore.dto.compilation.NewCompilationDto;
import ru.masnaviev.explore.dto.compilation.UpdateCompilationRequest;
import ru.masnaviev.explore.model.Compilation;
import ru.masnaviev.explore.model.Event;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CompilationService {

    private CompilationRepository repository;
    private CompilationConverter converter;

    private EventRepository eventRepository;

    public CompilationDto createCompilation(NewCompilationDto compilationDto) {
        log.debug("Добавление новой подборки, compilationDto = {}", compilationDto);
        List<Event> events = new ArrayList<>();
        if (compilationDto.getEvents() != null) {
            events = eventRepository.findAllById(compilationDto.getEvents());
        }

        Compilation compilation = new Compilation();
        compilation.setEvents(events);
        compilation.setTitle(compilationDto.getTitle());
        compilation.setPinned(compilationDto.isPinned());

        return converter.compilationConvertToCompilationDto(repository.save(compilation));
    }


    public ResponseEntity<HttpStatus> deleteCompilation(Integer compId) {
        log.debug("Удаление подборки, compilationDto = {}", compId);
        if (!repository.existsById(compId)) {
            throw new EntityNotFoundException("Подборки с id = " + compId + " не существует.");
        }
        repository.deleteById(compId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public CompilationDto updateCompilation(Integer compId, UpdateCompilationRequest request) {
        log.debug("Обновление подборки compId = {}, request = {}", compId, request);
        if (!repository.existsById(compId)) {
            throw new EntityNotFoundException("Подборки с id = " + compId + " не существует.");
        }
        Compilation compilation = repository.getReferenceById(compId);
        updateCompilation(compilation, request);
        return converter.compilationConvertToCompilationDto(repository.save(compilation));
    }

    private void updateCompilation(Compilation compilation, UpdateCompilationRequest request) {
        if (request.getEvents() != null) {
            List<Event> events = eventRepository.findAllById(request.getEvents());
            compilation.setEvents(events);
        }
        if (request.getTitle() != null) compilation.setTitle(request.getTitle());
        if(request.getPinned() != null) compilation.setPinned(request.getPinned());
    }
}
