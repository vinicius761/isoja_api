package com.example.demo.Veiculo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    private final VeiculoRepository veiculoRepository;

    public VeiculoController(VeiculoRepository veiculoRepository) {
        this.veiculoRepository = veiculoRepository;
    }

    @GetMapping
    public ResponseEntity<List<Veiculo>> listarTodos() {
        return ResponseEntity.ok(veiculoRepository.findAll());
    }

    @GetMapping("/{cod}")
    public ResponseEntity<Veiculo> buscarPorCod(@PathVariable String cod) {
        return veiculoRepository.findByCod(cod)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tag/{tag}")
    public ResponseEntity<Veiculo> buscarPorTag(@PathVariable String tag) {
        return veiculoRepository.findByTag(tag)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> criar(@RequestBody Veiculo veiculo) {
        veiculoRepository.save(veiculo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{cod}/tag")
    public ResponseEntity<Void> atualizarTag(@PathVariable String cod, @RequestParam String tag) {
        veiculoRepository.updateTag(cod, tag);
        return ResponseEntity.noContent().build();
    }
}
