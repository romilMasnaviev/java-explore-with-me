package ru.masnaviev.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.masnaviev.converter.CompilationConverter;
import ru.masnaviev.dao.CompilationRepository;
import ru.masnaviev.dao.EventRepository;
import ru.masnaviev.dto.compilation.CompilationDto;
import ru.masnaviev.dto.compilation.NewCompilationDto;
import ru.masnaviev.dto.compilation.UpdateCompilationRequest;
import ru.masnaviev.model.Compilation;
import ru.masnaviev.model.Event;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CompilationService {

    private CompilationRepository repository;
    private CompilationConverter converter;

    private EventRepository eventRepository;

    public ResponseEntity<CompilationDto> createCompilation(NewCompilationDto compilationDto) {
        log.debug("Добавление новой подборки, compilationDto = {}", compilationDto);
        List<Event> events = new ArrayList<>();
        if (compilationDto.getEvents() != null) {
            events = eventRepository.findAllById(compilationDto.getEvents());
        }

        Compilation compilation = new Compilation();
        compilation.setEvents(events);
        compilation.setTitle(compilationDto.getTitle());
        compilation.setPinned(compilationDto.isPinned());

        return new ResponseEntity<>(converter.compilationConvertToCompilationDto(repository.save(compilation)), HttpStatus.CREATED);
    }

    public List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size) {
        log.debug("Получение подборок, pinned = {}, from = {}, size = {}", pinned, from, size);
        Pageable pageable = PageRequest.of(from, size);
        List<Compilation> compilations = repository.findAllByPinned(pinned, pageable);
        return converter.compilationConvertToCompilationDto(compilations);
    }

    public ResponseEntity<CompilationDto> getCompilationById(Integer compId) {
        Compilation compilation = repository.getReferenceById(compId);
        return new ResponseEntity<>(converter.compilationConvertToCompilationDto(compilation), HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> deleteCompilation(Integer compId) {
        log.debug("Удаление подборки, compilationDto = {}", compId);
        repository.deleteById(compId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public CompilationDto updateCompilation(Integer compId, UpdateCompilationRequest request) {
        log.debug("Обновление подборки compId = {}, request = {}", compId, request);
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
        if (request.getPinned() != null) compilation.setPinned(request.getPinned());
    }
}
