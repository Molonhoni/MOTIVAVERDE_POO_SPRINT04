package br.com.motivaverde.service;

import br.com.motivaverde.dto.IntervencaoOperacionalRequest;
import br.com.motivaverde.dto.IntervencaoOperacionalResponse;
import br.com.motivaverde.exception.RecursoNaoEncontradoException;
import br.com.motivaverde.exception.RegraNegocioException;
import br.com.motivaverde.model.IntervencaoOperacional;
import br.com.motivaverde.model.TipoIntervencao;
import br.com.motivaverde.model.TrechoRodovia;
import br.com.motivaverde.repository.IntervencaoOperacionalRepository;
import br.com.motivaverde.repository.TrechoRodoviaRepository;
import org.springframework.stereotype.Service;

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

    public List<IntervencaoOperacionalResponse> listarTodas() {
        return intervencaoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public Optional<IntervencaoOperacionalResponse> buscarPorId(Long id) {
        return intervencaoRepository.findById(id)
                .map(this::toResponse);
    }

    public IntervencaoOperacionalResponse criar(
            IntervencaoOperacionalRequest request
    ) {
        validarAlturas(request);

        IntervencaoOperacional intervencao =
                new IntervencaoOperacional();

        preencherIntervencao(intervencao, request);

        IntervencaoOperacional salva =
                intervencaoRepository.save(intervencao);

        return toResponse(salva);
    }

    public Optional<IntervencaoOperacionalResponse> atualizar(
            Long id,
            IntervencaoOperacionalRequest request
    ) {
        validarAlturas(request);

        return intervencaoRepository.findById(id)
                .map(intervencao -> {

                    preencherIntervencao(
                            intervencao,
                            request
                    );

                    IntervencaoOperacional salva =
                            intervencaoRepository.save(intervencao);

                    return toResponse(salva);
                });
    }

    public boolean remover(Long id) {

        if (!intervencaoRepository.existsById(id)) {
            return false;
        }

        intervencaoRepository.deleteById(id);
        return true;
    }

    public List<IntervencaoOperacionalResponse> buscarPorTipo(
            TipoIntervencao tipo
    ) {
        return intervencaoRepository
                .findByTipoIntervencao(tipo)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<IntervencaoOperacionalResponse> buscarPorPeriodo(
            LocalDate inicio,
            LocalDate fim
    ) {
        return intervencaoRepository
                .findByDataExecucaoBetween(inicio, fim)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private void preencherIntervencao(
            IntervencaoOperacional intervencao,
            IntervencaoOperacionalRequest request
    ) {

        TrechoRodovia trecho = trechoRepository
                .findById(request.getTrechoId())
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Trecho não encontrado com ID "
                                        + request.getTrechoId()
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

        if (request.getAlturaDepois()
                > request.getAlturaAntes()) {

            throw new RegraNegocioException(
                    "A altura após a intervenção não pode ser maior que a altura anterior"
            );
        }
    }

    private IntervencaoOperacionalResponse toResponse(
            IntervencaoOperacional intervencao
    ) {

        return new IntervencaoOperacionalResponse(
                intervencao.getId(),
                intervencao.getTrecho().getId(),
                intervencao.getTrecho().getQuilometro(),
                intervencao.getTipoIntervencao(),
                intervencao.getDataExecucao(),
                intervencao.getAlturaAntes(),
                intervencao.getAlturaDepois()
        );
    }
}