package br.com.motivaverde.dto;

import br.com.motivaverde.model.TipoIntervencao;

import java.time.LocalDate;

public record IntervencaoOperacionalResponse(
        Long id,
        Long trechoId,
        Double quilometroTrecho,
        TipoIntervencao tipoIntervencao,
        LocalDate dataExecucao,
        Double alturaAntes,
        Double alturaDepois
) {
}