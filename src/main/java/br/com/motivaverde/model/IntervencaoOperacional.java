package br.com.motivaverde.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

@Entity
@Table(name = "TB_INTERVENCAO_OPERACIONAL")
public class IntervencaoOperacional {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_intervencao_operacional"
    )
    @SequenceGenerator(
            name = "seq_intervencao_operacional",
            sequenceName = "SEQ_INTERVENCAO_OPERACIONAL",
            allocationSize = 1
    )
    @Column(name = "ID_INTERVENCAO")
    private Long id;

    @NotNull(message = "O trecho é obrigatório")
    @ManyToOne
    @JoinColumn(name = "ID_TRECHO", nullable = false)
    private TrechoRodovia trecho;

    @NotNull(message = "O tipo da intervenção é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_INTERVENCAO", nullable = false, length = 30)
    private TipoIntervencao tipoIntervencao;

    @NotNull(message = "A data de execução é obrigatória")
    @Column(name = "DATA_EXECUCAO", nullable = false)
    private LocalDate dataExecucao;

    @NotNull(message = "A altura antes da intervenção é obrigatória")
    @PositiveOrZero(message = "A altura antes não pode ser negativa")
    @Column(name = "ALTURA_ANTES", nullable = false)
    private Double alturaAntes;

    @NotNull(message = "A altura depois da intervenção é obrigatória")
    @PositiveOrZero(message = "A altura depois não pode ser negativa")
    @Column(name = "ALTURA_DEPOIS", nullable = false)
    private Double alturaDepois;

    public IntervencaoOperacional() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TrechoRodovia getTrecho() {
        return trecho;
    }

    public void setTrecho(TrechoRodovia trecho) {
        this.trecho = trecho;
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