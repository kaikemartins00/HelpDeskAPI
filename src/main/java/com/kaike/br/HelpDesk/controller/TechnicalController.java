package com.kaike.br.HelpDesk.controller;

import com.kaike.br.HelpDesk.dto.TechnicalDto;
import com.kaike.br.HelpDesk.service.TechnicalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/technical")
public class TechnicalController {
    private final TechnicalService service;

    @GetMapping
    public ResponseEntity<List<TechnicalDto>> findAll(){
        List<TechnicalDto> list = service.findAll();
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<TechnicalDto> saveTechnical(@Valid @RequestBody TechnicalDto dto) {
        TechnicalDto savedDto = service.saveTechnical(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedDto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(savedDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTechnical(@PathVariable Long id) {
        service.deleteTechnical(id);

        return ResponseEntity.noContent().build();
    }

}
