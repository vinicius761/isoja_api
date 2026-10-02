package com.example.demo.Acoplamento;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/acoplamento")
public class AcoplamentoController {

    private final AcoplamentoRepository acoplamentoRepository;

    public AcoplamentoController(AcoplamentoRepository acoplamentoRepository) {
        this.acoplamentoRepository = acoplamentoRepository;
    }

    @GetMapping
    public ResponseEntity<List<Acoplamento>> getAll() {
        List<Acoplamento> acoplamentos = acoplamentoRepository.findAll();
        return ResponseEntity.ok(acoplamentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Acoplamento> getById(@PathVariable Long id) {
        return acoplamentoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Acoplamento> create(@RequestBody Acoplamento acoplamento) {
        Acoplamento novoAcoplamento = acoplamentoRepository.save(acoplamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoAcoplamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody Acoplamento acoplamento) {
        int rowsUpdated = acoplamentoRepository.update(id, acoplamento);
        if (rowsUpdated > 0) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        int rowsDeleted = acoplamentoRepository.deleteById(id);
        if (rowsDeleted > 0) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}