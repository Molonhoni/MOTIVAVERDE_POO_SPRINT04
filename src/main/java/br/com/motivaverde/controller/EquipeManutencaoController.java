package br.com.motivaverde.controller;

import br.com.motivaverde.model.EquipeManutencao;
import br.com.motivaverde.service.EquipeManutencaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipes")
public class EquipeManutencaoController {

    private final EquipeManutencaoService service;

    public EquipeManutencaoController(
            EquipeManutencaoService service
    ) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<EquipeManutencao>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipeManutencao> buscarPorId(
            @PathVariable Long id
    ) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.notFound().build()
                );
    }

    @PostMapping
    public ResponseEntity<EquipeManutencao> criar(
            @Valid @RequestBody EquipeManutencao equipe
    ) {

        EquipeManutencao criada = service.criar(equipe);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(criada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipeManutencao> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody EquipeManutencao equipe
    ) {

        return service.atualizar(id, equipe)
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.notFound().build()
                );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(
            @PathVariable Long id
    ) {

        if (!service.remover(id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/especialidade")
    public ResponseEntity<List<EquipeManutencao>>
            buscarPorEspecialidade(
                    @RequestParam String valor
            ) {

        return ResponseEntity.ok(
                service.buscarPorEspecialidade(valor)
        );
    }
}