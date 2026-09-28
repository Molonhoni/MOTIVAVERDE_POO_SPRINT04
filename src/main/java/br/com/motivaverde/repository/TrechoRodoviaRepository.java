package br.com.motivaverde.repository;

import br.com.motivaverde.model.TipoTrecho;
import br.com.motivaverde.model.TrechoRodovia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrechoRodoviaRepository
        extends JpaRepository<TrechoRodovia, Long> {

    List<TrechoRodovia> findByAlturaVegetacaoGreaterThanEqual(
            Double minimo
    );

    List<TrechoRodovia> findByTipoTrecho(
            TipoTrecho tipoTrecho
    );
}