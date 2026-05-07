package com.kaike.br.HelpDesk.controller;

import com.kaike.br.HelpDesk.database.model.CalledEntity;
import com.kaike.br.HelpDesk.dto.CalledDto;
import com.kaike.br.HelpDesk.dto.CalledUpdateDto;
import com.kaike.br.HelpDesk.service.CalledService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/called")
public class CalledController {
    private final CalledService service;

    @GetMapping
    public ResponseEntity<List<CalledDto>> findAll(){
        List<CalledDto> list = service.findAll();
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<CalledDto> saveCalled(@Valid @RequestBody CalledDto dto) {
        CalledDto savedDto = service.saveCalled(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedDto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(savedDto);
    }

    @PutMapping("/{id}/start")
    public void startService(@PathVariable Long id) {
        service.startService(id);
    }

    @PutMapping("/{id}/finish")
    public void closeService(@PathVariable Long id) {
        service.closeService(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CalledEntity> updateAllCalled(
            @PathVariable Long id,
            @RequestBody CalledUpdateDto dto) {

        CalledEntity calledUpdate = service.updateAllCalled(id, dto);

        return ResponseEntity.ok(calledUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCalled(@PathVariable Long id) {
        service.deleteCalled(id);

        return ResponseEntity.noContent().build();
    }

}
