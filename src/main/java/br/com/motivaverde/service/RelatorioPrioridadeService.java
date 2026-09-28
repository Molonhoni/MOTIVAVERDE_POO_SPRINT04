package br.com.motivaverde.service;

import br.com.motivaverde.model.PrioridadeIntervencao;
import br.com.motivaverde.model.RelatorioPrioridade;
import br.com.motivaverde.model.TrechoRodovia;
import br.com.motivaverde.repository.RelatorioPrioridadeRepository;
import br.com.motivaverde.repository.TrechoRodoviaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RelatorioPrioridadeService {

    private final RelatorioPrioridadeRepository relatorioRepository;
    private final TrechoRodoviaRepository trechoRepository;
    private final MotorPrioridadeService motorPrioridade;

    public RelatorioPrioridadeService(
            RelatorioPrioridadeRepository relatorioRepository,
            TrechoRodoviaRepository trechoRepository,
            MotorPrioridadeService motorPrioridade
    ) {
        this.relatorioRepository = relatorioRepository;
        this.trechoRepository = trechoRepository;
        this.motorPrioridade = motorPrioridade;
    }

    public RelatorioPrioridade gerarRelatorio() {

        List<TrechoRodovia> trechos = trechoRepository.findAll();

        int qtBaixa = 0;
        int qtModerada = 0;
        int qtAlta = 0;
        int qtUrgente = 0;

        for (TrechoRodovia trecho : trechos) {

            PrioridadeIntervencao prioridade =
                    motorPrioridade.classificar(trecho);

            switch (prioridade) {
                case BAIXA -> qtBaixa++;
                case MODERADA -> qtModerada++;
                case ALTA -> qtAlta++;
                case URGENTE -> qtUrgente++;
            }
        }

        String resumo =
                "Relatório Motiva Verde | Baixa: " + qtBaixa
                + " | Moderada: " + qtModerada
                + " | Alta: " + qtAlta
                + " | Urgente: " + qtUrgente;

        RelatorioPrioridade relatorio =
                new RelatorioPrioridade();

        relatorio.setDataGeracao(LocalDateTime.now());
        relatorio.setQtBaixa(qtBaixa);
        relatorio.setQtModerada(qtModerada);
        relatorio.setQtAlta(qtAlta);
        relatorio.setQtUrgente(qtUrgente);
        relatorio.setResumo(resumo);

        return relatorioRepository.save(relatorio);
    }

    public List<RelatorioPrioridade> listarHistorico() {
        return relatorioRepository.findAll();
    }

    public List<RelatorioPrioridade> buscarPorPeriodo(
            LocalDate inicio,
            LocalDate fim
    ) {

        if (inicio.isAfter(fim)) {
            throw new IllegalArgumentException(
                    "A data inicial não pode ser posterior à data final"
            );
        }

        LocalDateTime inicioPeriodo =
                inicio.atStartOfDay();

        LocalDateTime fimPeriodo =
                fim.plusDays(1).atStartOfDay().minusNanos(1);

        return relatorioRepository.findByDataGeracaoBetween(
                inicioPeriodo,
                fimPeriodo
        );
    }
}