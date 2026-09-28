package br.com.motivaverde.dto;

import br.com.motivaverde.model.CondicaoCrescimento;
import br.com.motivaverde.model.TipoTrecho;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class TrechoRodoviaRequest {

    @NotNull(message = "O quilômetro é obrigatório")
    @PositiveOrZero(message = "O quilômetro não pode ser negativo")
    private Double quilometro;

    @NotNull(message = "A altura da vegetação é obrigatória")
    @PositiveOrZero(message = "A altura da vegetação não pode ser negativa")
    private Double alturaVegetacao;

    @NotNull(message = "A condição de crescimento é obrigatória")
    private CondicaoCrescimento condicaoCrescimento;

    private Long equipeId;

    @NotNull(message = "O tipo do trecho é obrigatório")
    private TipoTrecho tipoTrecho;

    private String codigoSensor;

    public Double getQuilometro() {
        return quilometro;
    }

    public void setQuilometro(Double quilometro) {
        this.quilometro = quilometro;
    }

    public Double getAlturaVegetacao() {
        return alturaVegetacao;
    }

    public void setAlturaVegetacao(Double alturaVegetacao) {
        this.alturaVegetacao = alturaVegetacao;
    }

    public CondicaoCrescimento getCondicaoCrescimento() {
        return condicaoCrescimento;
    }

    public void setCondicaoCrescimento(
            CondicaoCrescimento condicaoCrescimento
    ) {
        this.condicaoCrescimento = condicaoCrescimento;
    }

    public Long getEquipeId() {
        return equipeId;
    }

    public void setEquipeId(Long equipeId) {
        this.equipeId = equipeId;
    }

    public TipoTrecho getTipoTrecho() {
        return tipoTrecho;
    }

    public void setTipoTrecho(TipoTrecho tipoTrecho) {
        this.tipoTrecho = tipoTrecho;
    }

    public String getCodigoSensor() {
        return codigoSensor;
    }

    public void setCodigoSensor(String codigoSensor) {
        this.codigoSensor = codigoSensor;
    }
}