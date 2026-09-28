package br.com.motivaverde.service;

import br.com.motivaverde.dto.IntervencaoOperacionalRequest;
import br.com.motivaverde.model.IntervencaoOperacional;
import br.com.motivaverde.model.TipoIntervencao;
import br.com.motivaverde.model.TrechoRodovia;
import br.com.motivaverde.repository.IntervencaoOperacionalRepository;
import br.com.motivaverde.repository.TrechoRodoviaRepository;
import org.springframework.stereotype.Service;
import br.com.motivaverde.exception.RecursoNaoEncontradoException;
import br.com.motivaverde.exception.RegraNegocioException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class IntervencaoOperacionalService {

    private final IntervencaoOperacionalRepository intervencaoRepository;
    private final TrechoRodoviaRepository trechoRepository;

    public IntervencaoOperacionalService(
            IntervencaoOperacionalRepository intervencaoRepository,
            TrechoRodoviaRepository trechoRepository
    ) {
        this.intervencaoRepository = intervencaoRepository;
        this.trechoRepository = trechoRepository;
    }

    public List<IntervencaoOperacional> listarTodas() {
        return intervencaoRepository.findAll();
    }

    public Optional<IntervencaoOperacional> buscarPorId(Long id) {
        return intervencaoRepository.findById(id);
    }

    public IntervencaoOperacional criar(
            IntervencaoOperacionalRequest request
    ) {
        validarAlturas(request);

        IntervencaoOperacional intervencao =
                new IntervencaoOperacional();

        preencherIntervencao(intervencao, request);

        return intervencaoRepository.save(intervencao);
    }

    public Optional<IntervencaoOperacional> atualizar(
            Long id,
            IntervencaoOperacionalRequest request
    ) {
        validarAlturas(request);

        return intervencaoRepository.findById(id)
                .map(intervencao -> {
                    preencherIntervencao(intervencao, request);
                    return intervencaoRepository.save(intervencao);
                });
    }

    public boolean remover(Long id) {

        if (!intervencaoRepository.existsById(id)) {
            return false;
        }

        intervencaoRepository.deleteById(id);
        return true;
    }

    public List<IntervencaoOperacional> buscarPorTipo(
            TipoIntervencao tipo
    ) {
        return intervencaoRepository.findByTipoIntervencao(tipo);
    }

    public List<IntervencaoOperacional> buscarPorPeriodo(
            LocalDate inicio,
            LocalDate fim
    ) {
        return intervencaoRepository
                .findByDataExecucaoBetween(inicio, fim);
    }

    private void preencherIntervencao(
            IntervencaoOperacional intervencao,
            IntervencaoOperacionalRequest request
    ) {

        TrechoRodovia trecho = trechoRepository
                .findById(request.getTrechoId())
               .orElseThrow(() ->
        new RecursoNaoEncontradoException(
                "Trecho não encontrado com ID " + request.getTrechoId()
        )
);

        intervencao.setTrecho(trecho);
        intervencao.setTipoIntervencao(
                request.getTipoIntervencao()
        );
        intervencao.setDataExecucao(
                request.getDataExecucao()
        );
        intervencao.setAlturaAntes(
                request.getAlturaAntes()
        );
        intervencao.setAlturaDepois(
                request.getAlturaDepois()
        );
    }

    private void validarAlturas(
            IntervencaoOperacionalRequest request
    ) {
        if (request.getAlturaDepois() > request.getAlturaAntes()) {
           throw new RegraNegocioException(
        "A altura após a intervenção não pode ser maior que a altura anterior"
);
        }
    }
}