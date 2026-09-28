package br.com.motivaverde.service;

import br.com.motivaverde.model.PrioridadeIntervencao;
import br.com.motivaverde.model.TrechoRodovia;
import org.springframework.stereotype.Service;
import br.com.motivaverde.exception.RegraNegocioException;

@Service
public class MotorPrioridadeService {

    public PrioridadeIntervencao classificar(
            TrechoRodovia trecho
    ) {
        return classificar(trecho.getAlturaVegetacao());
    }

    public PrioridadeIntervencao classificar(
            Double alturaVegetacao
    ) {

        if (alturaVegetacao == null || alturaVegetacao < 0) {
            throw new RegraNegocioException(
        "A altura da vegetação deve ser maior ou igual a zero"
);
        }

        if (alturaVegetacao >= 30) {
            return PrioridadeIntervencao.URGENTE;
        }

        if (alturaVegetacao >= 21) {
            return PrioridadeIntervencao.ALTA;
        }

        if (alturaVegetacao > 15) {
            return PrioridadeIntervencao.MODERADA;
        }

        return PrioridadeIntervencao.BAIXA;
    }
}