package com.kaike.br.HelpDesk.repository;

import com.kaike.br.HelpDesk.database.model.CalledEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalledRepository extends JpaRepository<CalledEntity, Long> {

}
