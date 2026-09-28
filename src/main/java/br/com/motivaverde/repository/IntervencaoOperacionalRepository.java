package br.com.motivaverde.repository;

import br.com.motivaverde.model.IntervencaoOperacional;
import br.com.motivaverde.model.TipoIntervencao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface IntervencaoOperacionalRepository
        extends JpaRepository<IntervencaoOperacional, Long> {

    List<IntervencaoOperacional> findByTipoIntervencao(
            TipoIntervencao tipoIntervencao
    );

    List<IntervencaoOperacional> findByDataExecucaoBetween(
            LocalDate inicio,
            LocalDate fim
    );
}