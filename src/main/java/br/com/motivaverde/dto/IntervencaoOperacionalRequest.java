package br.com.motivaverde.dto;

import br.com.motivaverde.model.TipoIntervencao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

public class IntervencaoOperacionalRequest {

    @NotNull(message = "O ID do trecho é obrigatório")
    private Long trechoId;

    @NotNull(message = "O tipo da intervenção é obrigatório")
    private TipoIntervencao tipoIntervencao;

    @NotNull(message = "A data de execução é obrigatória")
    private LocalDate dataExecucao;

    @NotNull(message = "A altura antes é obrigatória")
    @PositiveOrZero(message = "A altura antes não pode ser negativa")
    private Double alturaAntes;

    @NotNull(message = "A altura depois é obrigatória")
    @PositiveOrZero(message = "A altura depois não pode ser negativa")
    private Double alturaDepois;

    public Long getTrechoId() {
        return trechoId;
    }

    public void setTrechoId(Long trechoId) {
        this.trechoId = trechoId;
    }

    public TipoIntervencao getTipoIntervencao() {
        return tipoIntervencao;
    }

    public void setTipoIntervencao(TipoIntervencao tipoIntervencao) {
        this.tipoIntervencao = tipoIntervencao;
    }

    public LocalDate getDataExecucao() {
        return dataExecucao;
    }

    public void setDataExecucao(LocalDate dataExecucao) {
        this.dataExecucao = dataExecucao;
    }

    public Double getAlturaAntes() {
        return alturaAntes;
    }

    public void setAlturaAntes(Double alturaAntes) {
        this.alturaAntes = alturaAntes;
    }

    public Double getAlturaDepois() {
        return alturaDepois;
    }

    public void setAlturaDepois(Double alturaDepois) {
        this.alturaDepois = alturaDepois;
    }
}