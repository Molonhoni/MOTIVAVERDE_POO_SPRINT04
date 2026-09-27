package br.com.motivaverde.service;

import br.com.motivaverde.model.EquipeManutencao;
import br.com.motivaverde.repository.EquipeManutencaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipeManutencaoService {

    private final EquipeManutencaoRepository repository;

    public EquipeManutencaoService(EquipeManutencaoRepository repository) {
        this.repository = repository;
    }

    public List<EquipeManutencao> listarTodas() {
        return repository.findAll();
    }

    public Optional<EquipeManutencao> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public EquipeManutencao criar(EquipeManutencao equipe) {
        equipe.setId(null);
        return repository.save(equipe);
    }

    public Optional<EquipeManutencao> atualizar(
            Long id,
            EquipeManutencao dadosAtualizados
    ) {

        return repository.findById(id)
                .map(equipe -> {
                    equipe.setNome(dadosAtualizados.getNome());
                    equipe.setEspecialidade(
                            dadosAtualizados.getEspecialidade()
                    );

                    return repository.save(equipe);
                });
    }

    public boolean remover(Long id) {

        if (!repository.existsById(id)) {
            return false;
        }

        repository.deleteById(id);
        return true;
    }

    public List<EquipeManutencao> buscarPorEspecialidade(
            String especialidade
    ) {
        return repository.findByEspecialidadeIgnoreCase(especialidade);
    }
}