package br.com.motivaverde.repository;

import br.com.motivaverde.model.RelatorioPrioridade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RelatorioPrioridadeRepository
        extends JpaRepository<RelatorioPrioridade, Long> {

    List<RelatorioPrioridade> findByDataGeracaoBetween(
            LocalDateTime inicio,
            LocalDateTime fim
    );
}