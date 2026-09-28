package br.com.motivaverde.service;

import br.com.motivaverde.dto.TrechoRodoviaRequest;
import br.com.motivaverde.dto.TrechoRodoviaResponse;
import br.com.motivaverde.exception.RecursoNaoEncontradoException;
import br.com.motivaverde.model.EquipeManutencao;
import br.com.motivaverde.model.TipoTrecho;
import br.com.motivaverde.model.TrechoRodovia;
import br.com.motivaverde.repository.EquipeManutencaoRepository;
import br.com.motivaverde.repository.TrechoRodoviaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrechoRodoviaService {

    private final TrechoRodoviaRepository trechoRepository;
    private final EquipeManutencaoRepository equipeRepository;

    public TrechoRodoviaService(
            TrechoRodoviaRepository trechoRepository,
            EquipeManutencaoRepository equipeRepository
    ) {
        this.trechoRepository = trechoRepository;
        this.equipeRepository = equipeRepository;
    }

    public List<TrechoRodoviaResponse> listarTodos() {
        return trechoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public Optional<TrechoRodoviaResponse> buscarPorId(Long id) {
        return trechoRepository.findById(id)
                .map(this::toResponse);
    }

    public TrechoRodoviaResponse criar(
            TrechoRodoviaRequest request
    ) {
        TrechoRodovia trecho = new TrechoRodovia();

        preencherTrecho(trecho, request);

        TrechoRodovia salvo =
                trechoRepository.save(trecho);

        return toResponse(salvo);
    }

    public Optional<TrechoRodoviaResponse> atualizar(
            Long id,
            TrechoRodoviaRequest request
    ) {
        return trechoRepository.findById(id)
                .map(trecho -> {

                    preencherTrecho(trecho, request);

                    TrechoRodovia salvo =
                            trechoRepository.save(trecho);

                    return toResponse(salvo);
                });
    }

    public boolean remover(Long id) {

        if (!trechoRepository.existsById(id)) {
            return false;
        }

        trechoRepository.deleteById(id);
        return true;
    }

    public List<TrechoRodoviaResponse> buscarPorAlturaMinima(
            Double minimo
    ) {
        return trechoRepository
                .findByAlturaVegetacaoGreaterThanEqual(minimo)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<TrechoRodoviaResponse> buscarPorTipo(
            TipoTrecho tipo
    ) {
        return trechoRepository
                .findByTipoTrecho(tipo)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private void preencherTrecho(
            TrechoRodovia trecho,
            TrechoRodoviaRequest request
    ) {

        trecho.setQuilometro(
                request.getQuilometro()
        );

        trecho.setAlturaVegetacao(
                request.getAlturaVegetacao()
        );

        trecho.setCondicaoCrescimento(
                request.getCondicaoCrescimento()
        );

        trecho.setTipoTrecho(
                request.getTipoTrecho()
        );

        trecho.setCodigoSensor(
                request.getCodigoSensor()
        );

        if (request.getEquipeId() == null) {
            trecho.setEquipeResponsavel(null);
            return;
        }

        EquipeManutencao equipe = equipeRepository
                .findById(request.getEquipeId())
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Equipe não encontrada com ID "
                                        + request.getEquipeId()
                        )
                );

        trecho.setEquipeResponsavel(equipe);
    }

    private TrechoRodoviaResponse toResponse(
            TrechoRodovia trecho
    ) {

        Long equipeId = null;
        String equipeNome = null;

        if (trecho.getEquipeResponsavel() != null) {
            equipeId =
                    trecho.getEquipeResponsavel().getId();

            equipeNome =
                    trecho.getEquipeResponsavel().getNome();
        }

        return new TrechoRodoviaResponse(
                trecho.getId(),
                trecho.getQuilometro(),
                trecho.getAlturaVegetacao(),
                trecho.getCondicaoCrescimento(),
                equipeId,
                equipeNome,
                trecho.getTipoTrecho(),
                trecho.getCodigoSensor()
        );
    }
}