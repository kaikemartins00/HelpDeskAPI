package com.kaike.br.HelpDesk.service;

import com.kaike.br.HelpDesk.database.model.TechnicalEntity;
import com.kaike.br.HelpDesk.dto.TechnicalDto;
import com.kaike.br.HelpDesk.exception.ResourceNotFoundException;
import com.kaike.br.HelpDesk.repository.TechnicalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TechnicalService {
    private final TechnicalRepository repository;

    private TechnicalDto toDTO(TechnicalEntity entity) {
        return TechnicalDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .specialty(entity.getSpecialty())
                .build();
    }

    public List<TechnicalDto> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public TechnicalDto saveTechnical(TechnicalDto dto) {

        TechnicalEntity entity = TechnicalEntity.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .specialty(dto.getSpecialty())
                .build();

        TechnicalEntity saved = repository.save(entity);

        return TechnicalDto.builder()
                .id(saved.getId())
                .name(saved.getName())
                .email(saved.getEmail())
                .specialty(saved.getSpecialty())
                .build();
    }

    public void deleteTechnical(Long id){
        TechnicalEntity technical = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Technical not found"));

        repository.delete(technical);
    }

}
