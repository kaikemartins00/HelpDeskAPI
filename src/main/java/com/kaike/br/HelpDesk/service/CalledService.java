package com.kaike.br.HelpDesk.service;

import com.kaike.br.HelpDesk.database.model.*;
import com.kaike.br.HelpDesk.dto.CalledDto;
import com.kaike.br.HelpDesk.dto.CalledUpdateDto;
import com.kaike.br.HelpDesk.exception.BusinessException;
import com.kaike.br.HelpDesk.exception.ResourceNotFoundException;
import com.kaike.br.HelpDesk.repository.CalledRepository;
import com.kaike.br.HelpDesk.repository.TechnicalRepository;
import com.kaike.br.HelpDesk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalledService {
    private final CalledRepository repository;
    private final UserRepository userRepository;
    private final TechnicalRepository technicalRepository;

    private CalledDto toDTO(CalledEntity entity) {

        return CalledDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .priority(entity.getPriority())
                .status(entity.getStatus())
                .openingDate(entity.getOpeningDate())
                .observation(entity.getObservation())

                .userId(entity.getUser().getId())
                .technicalId(entity.getTechnical().getId())

                .build();
    }

    public List<CalledDto> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public CalledDto saveCalled(CalledDto dto) {

        UserEntity user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        TechnicalEntity technical = technicalRepository.findById(dto.getTechnicalId())
                .orElseThrow(() -> new ResourceNotFoundException("Technical not found"));

        CalledEntity entity = CalledEntity.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .observation(dto.getObservation())
                .user(user)
                .technical(technical)
                .build();

        entity.setStatus(CalledStatus.OPEN);
        entity.setOpeningDate(Instant.now());

        String title = dto.getTitle() != null ? dto.getTitle() : "";
        String description = dto.getDescription() != null ? dto.getDescription() : "";

        String text = (title + " " + description).toLowerCase();

        if (text.contains("servidor") || text.contains("sistema fora")) {
            entity.setPriority(CalledPriority.HIGH);
        } else if (text.contains("lento") || text.contains("bug")) {
            entity.setPriority(CalledPriority.MEDIUM);
        } else {
            entity.setPriority(CalledPriority.LOW);
        }

        CalledEntity saved = repository.save(entity);

        return toDTO(saved);
    }

    public void updateDataCalled(CalledEntity entity, CalledUpdateDto dto) {
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setObservation(dto.getObservation());
    }

    public CalledEntity updateAllCalled(Long id, CalledUpdateDto dto) {
        CalledEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Call not found"));

        updateDataCalled(entity, dto);

        return repository.save(entity);
    }

    public void startService(Long id) {
        CalledEntity called = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Call not found"));

        if (called.getStatus() != CalledStatus.OPEN) {
            throw new BusinessException("The call cannot be initiated.");
        }

        called.setStatus(CalledStatus.IN_PROGRESS);

        repository.save(called);
    }

    public void closeService(Long id) {
        CalledEntity called = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Call not found"));

        if (called.getStatus() != CalledStatus.IN_PROGRESS) {
            throw new BusinessException("The call cannot be closed");
        }

        called.setStatus(CalledStatus.COMPLETED);

        repository.save(called);
    }

    public void deleteCalled(Long id){
        repository.deleteById(id);
    }

}
