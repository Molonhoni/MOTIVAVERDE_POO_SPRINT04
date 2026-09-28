package br.com.motivaverde.service;

import br.com.motivaverde.dto.TrechoRodoviaRequest;
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

    public List<TrechoRodovia> listarTodos() {
        return trechoRepository.findAll();
    }

    public Optional<TrechoRodovia> buscarPorId(Long id) {
        return trechoRepository.findById(id);
    }

    public TrechoRodovia criar(TrechoRodoviaRequest request) {

        TrechoRodovia trecho = new TrechoRodovia();

        preencherTrecho(trecho, request);

        return trechoRepository.save(trecho);
    }

    public Optional<TrechoRodovia> atualizar(
            Long id,
            TrechoRodoviaRequest request
    ) {

        return trechoRepository.findById(id)
                .map(trecho -> {
                    preencherTrecho(trecho, request);
                    return trechoRepository.save(trecho);
                });
    }

    public boolean remover(Long id) {

        if (!trechoRepository.existsById(id)) {
            return false;
        }

        trechoRepository.deleteById(id);
        return true;
    }

    public List<TrechoRodovia> buscarPorAlturaMinima(
            Double minimo
    ) {
        return trechoRepository
                .findByAlturaVegetacaoGreaterThanEqual(minimo);
    }

    public List<TrechoRodovia> buscarPorTipo(
            TipoTrecho tipo
    ) {
        return trechoRepository.findByTipoTrecho(tipo);
    }

    private void preencherTrecho(
            TrechoRodovia trecho,
            TrechoRodoviaRequest request
    ) {

        trecho.setQuilometro(request.getQuilometro());
        trecho.setAlturaVegetacao(request.getAlturaVegetacao());
        trecho.setCondicaoCrescimento(
                request.getCondicaoCrescimento()
        );
        trecho.setTipoTrecho(request.getTipoTrecho());
        trecho.setCodigoSensor(request.getCodigoSensor());

        if (request.getEquipeId() == null) {
            trecho.setEquipeResponsavel(null);
            return;
        }

        EquipeManutencao equipe = equipeRepository
                .findById(request.getEquipeId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Equipe não encontrada"
                        )
                );

        trecho.setEquipeResponsavel(equipe);
    }
}