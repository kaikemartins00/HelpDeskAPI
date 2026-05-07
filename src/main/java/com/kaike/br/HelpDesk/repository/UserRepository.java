package com.kaike.br.HelpDesk.repository;

import com.kaike.br.HelpDesk.database.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
