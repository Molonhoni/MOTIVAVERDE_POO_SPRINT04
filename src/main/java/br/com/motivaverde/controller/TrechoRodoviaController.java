package br.com.motivaverde.controller;

import br.com.motivaverde.dto.TrechoRodoviaRequest;
import br.com.motivaverde.model.TipoTrecho;
import br.com.motivaverde.model.TrechoRodovia;
import br.com.motivaverde.service.TrechoRodoviaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trechos")
public class TrechoRodoviaController {

    private final TrechoRodoviaService service;

    public TrechoRodoviaController(
            TrechoRodoviaService service
    ) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TrechoRodovia>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrechoRodovia> buscarPorId(
            @PathVariable Long id
    ) {

        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.notFound().build()
                );
    }

    @PostMapping
    public ResponseEntity<TrechoRodovia> criar(
            @Valid @RequestBody TrechoRodoviaRequest request
    ) {

        TrechoRodovia criado = service.criar(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrechoRodovia> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody TrechoRodoviaRequest request
    ) {

        return service.atualizar(id, request)
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

    @GetMapping("/altura-minima")
    public ResponseEntity<List<TrechoRodovia>>
            buscarPorAlturaMinima(
                    @RequestParam Double valor
            ) {

        return ResponseEntity.ok(
                service.buscarPorAlturaMinima(valor)
        );
    }

    @GetMapping("/tipo")
    public ResponseEntity<List<TrechoRodovia>>
            buscarPorTipo(
                    @RequestParam TipoTrecho valor
            ) {

        return ResponseEntity.ok(
                service.buscarPorTipo(valor)
        );
    }
}