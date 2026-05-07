package com.kaike.br.HelpDesk.repository;

import com.kaike.br.HelpDesk.database.model.TechnicalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechnicalRepository extends JpaRepository<TechnicalEntity, Long> {

}
