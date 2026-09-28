package br.com.motivaverde.dto;

import br.com.motivaverde.model.CondicaoCrescimento;
import br.com.motivaverde.model.TipoTrecho;

public record TrechoRodoviaResponse(
        Long id,
        Double quilometro,
        Double alturaVegetacao,
        CondicaoCrescimento condicaoCrescimento,
        Long equipeId,
        String equipeNome,
        TipoTrecho tipoTrecho,
        String codigoSensor
) {
}