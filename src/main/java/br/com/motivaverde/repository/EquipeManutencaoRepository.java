package br.com.motivaverde.repository;

import br.com.motivaverde.model.EquipeManutencao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipeManutencaoRepository
        extends JpaRepository<EquipeManutencao, Long> {

    List<EquipeManutencao> findByEspecialidadeIgnoreCase(String especialidade);
}