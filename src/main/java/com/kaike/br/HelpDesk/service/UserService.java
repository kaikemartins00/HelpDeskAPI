package com.kaike.br.HelpDesk.service;

import com.kaike.br.HelpDesk.database.model.UserEntity;
import com.kaike.br.HelpDesk.dto.UserDto;
import com.kaike.br.HelpDesk.dto.UserUpdateDto;
import com.kaike.br.HelpDesk.exception.ResourceNotFoundException;
import com.kaike.br.HelpDesk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    private UserDto toDTO(UserEntity entity) {
        return UserDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .sector(entity.getSector())
                .build();
    }

    public List<UserDto> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public UserDto saveUser(UserDto dto) {

        UserEntity entity = UserEntity.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .sector(dto.getSector())
                .build();

        UserEntity saved = repository.save(entity);

        return UserDto.builder()
                .id(saved.getId())
                .name(saved.getName())
                .email(saved.getEmail())
                .sector(saved.getSector())
                .build();
    }

    public void updateDataUser(UserEntity entity, UserUpdateDto dto) {
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
    }

    public UserEntity updateAllUser(Long id, UserUpdateDto dto) {
        UserEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        updateDataUser(entity, dto);

        return repository.save(entity);
    }

    public void deleteUser(Long id){
        UserEntity user = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        repository.delete(user);
    }

}
