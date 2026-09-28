package br.com.motivaverde.controller;

import br.com.motivaverde.model.RelatorioPrioridade;
import br.com.motivaverde.service.RelatorioPrioridadeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/relatorios")
public class RelatorioPrioridadeController {

    private final RelatorioPrioridadeService service;

    public RelatorioPrioridadeController(
            RelatorioPrioridadeService service
    ) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<RelatorioPrioridade> gerarRelatorio() {

        RelatorioPrioridade relatorio =
                service.gerarRelatorio();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(relatorio);
    }

    @GetMapping
    public ResponseEntity<List<RelatorioPrioridade>>
            listarHistorico() {

        return ResponseEntity.ok(
                service.listarHistorico()
        );
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<RelatorioPrioridade>>
            buscarPorPeriodo(
                    @RequestParam LocalDate inicio,
                    @RequestParam LocalDate fim
            ) {

        return ResponseEntity.ok(
                service.buscarPorPeriodo(inicio, fim)
        );
    }
}