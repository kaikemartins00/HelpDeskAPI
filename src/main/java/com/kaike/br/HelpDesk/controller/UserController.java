package com.kaike.br.HelpDesk.controller;

import com.kaike.br.HelpDesk.database.model.UserEntity;
import com.kaike.br.HelpDesk.dto.UserDto;
import com.kaike.br.HelpDesk.dto.UserUpdateDto;
import com.kaike.br.HelpDesk.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/user")
public class UserController {
    private final UserService service;

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll(){
        List<UserDto> list = service.findAll();
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<UserDto> saveUser(@Valid @RequestBody UserDto dto) {
        UserDto savedDto = service.saveUser(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedDto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(savedDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> updateAllUser(
            @PathVariable Long id,
            @RequestBody UserUpdateDto dto) {

        UserEntity userUpdate = service.updateAllUser(id, dto);

        return ResponseEntity.ok(userUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        service.deleteUser(id);

        return ResponseEntity.noContent().build();
    }

}
