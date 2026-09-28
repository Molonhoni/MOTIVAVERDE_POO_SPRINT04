package br.com.motivaverde.controller;

import br.com.motivaverde.dto.IntervencaoOperacionalRequest;
import br.com.motivaverde.dto.IntervencaoOperacionalResponse;
import br.com.motivaverde.model.TipoIntervencao;
import br.com.motivaverde.service.IntervencaoOperacionalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/intervencoes")
public class IntervencaoOperacionalController {

    private final IntervencaoOperacionalService service;

    public IntervencaoOperacionalController(
            IntervencaoOperacionalService service
    ) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<IntervencaoOperacionalResponse>>
            listarTodas() {

        return ResponseEntity.ok(
                service.listarTodas()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<IntervencaoOperacionalResponse>
            buscarPorId(
                    @PathVariable Long id
            ) {

        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.notFound().build()
                );
    }

    @PostMapping
    public ResponseEntity<IntervencaoOperacionalResponse>
            criar(
                    @Valid
                    @RequestBody
                    IntervencaoOperacionalRequest request
            ) {

        IntervencaoOperacionalResponse criada =
                service.criar(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(criada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IntervencaoOperacionalResponse>
            atualizar(
                    @PathVariable Long id,
                    @Valid
                    @RequestBody
                    IntervencaoOperacionalRequest request
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

    @GetMapping("/tipo")
    public ResponseEntity<List<IntervencaoOperacionalResponse>>
            buscarPorTipo(
                    @RequestParam TipoIntervencao valor
            ) {

        return ResponseEntity.ok(
                service.buscarPorTipo(valor)
        );
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<IntervencaoOperacionalResponse>>
            buscarPorPeriodo(
                    @RequestParam LocalDate inicio,
                    @RequestParam LocalDate fim
            ) {

        return ResponseEntity.ok(
                service.buscarPorPeriodo(inicio, fim)
        );
    }
}