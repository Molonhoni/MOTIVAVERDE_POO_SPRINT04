package br.com.motivaverde.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "TB_TRECHO_RODOVIA")
public class TrechoRodovia {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_trecho_rodovia"
    )
    @SequenceGenerator(
            name = "seq_trecho_rodovia",
            sequenceName = "SEQ_TRECHO_RODOVIA",
            allocationSize = 1
    )
    @Column(name = "ID_TRECHO")
    private Long id;

    @NotNull(message = "O quilômetro é obrigatório")
    @PositiveOrZero(message = "O quilômetro não pode ser negativo")
    @Column(name = "QUILOMETRO", nullable = false)
    private Double quilometro;

    @NotNull(message = "A altura da vegetação é obrigatória")
    @PositiveOrZero(message = "A altura da vegetação não pode ser negativa")
    @Column(name = "ALTURA_VEGETACAO", nullable = false)
    private Double alturaVegetacao;

    @NotNull(message = "A condição de crescimento é obrigatória")
    @Enumerated(EnumType.STRING)
    @Column(name = "CONDICAO_CRESCIMENTO", nullable = false, length = 30)
    private CondicaoCrescimento condicaoCrescimento;

    @ManyToOne
    @JoinColumn(name = "ID_EQUIPE")
    private EquipeManutencao equipeResponsavel;

    @NotNull(message = "O tipo do trecho é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_TRECHO", length = 20)
    private TipoTrecho tipoTrecho;

    @Column(name = "CODIGO_SENSOR", length = 50)
    private String codigoSensor;

    public TrechoRodovia() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public EquipeManutencao getEquipeResponsavel() {
        return equipeResponsavel;
    }

    public void setEquipeResponsavel(
            EquipeManutencao equipeResponsavel
    ) {
        this.equipeResponsavel = equipeResponsavel;
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