package br.com.motivaverde.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "TB_RELATORIO_PRIORIDADE")
public class RelatorioPrioridade {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_relatorio_prioridade"
    )
    @SequenceGenerator(
            name = "seq_relatorio_prioridade",
            sequenceName = "SEQ_RELATORIO_PRIORIDADE",
            allocationSize = 1
    )
    @Column(name = "ID_RELATORIO")
    private Long id;

    @Column(name = "DATA_GERACAO", nullable = false)
    private LocalDateTime dataGeracao;

    @Column(name = "QT_BAIXA", nullable = false)
    private Integer qtBaixa;

    @Column(name = "QT_MODERADA", nullable = false)
    private Integer qtModerada;

    @Column(name = "QT_ALTA", nullable = false)
    private Integer qtAlta;

    @Column(name = "QT_URGENTE", nullable = false)
    private Integer qtUrgente;

    @Column(name = "RESUMO", length = 1000)
    private String resumo;

    public RelatorioPrioridade() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataGeracao() {
        return dataGeracao;
    }

    public void setDataGeracao(LocalDateTime dataGeracao) {
        this.dataGeracao = dataGeracao;
    }

    public Integer getQtBaixa() {
        return qtBaixa;
    }

    public void setQtBaixa(Integer qtBaixa) {
        this.qtBaixa = qtBaixa;
    }

    public Integer getQtModerada() {
        return qtModerada;
    }

    public void setQtModerada(Integer qtModerada) {
        this.qtModerada = qtModerada;
    }

    public Integer getQtAlta() {
        return qtAlta;
    }

    public void setQtAlta(Integer qtAlta) {
        this.qtAlta = qtAlta;
    }

    public Integer getQtUrgente() {
        return qtUrgente;
    }

    public void setQtUrgente(Integer qtUrgente) {
        this.qtUrgente = qtUrgente;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }
}