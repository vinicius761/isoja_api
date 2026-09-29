package com.example.demo.Carreta;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carreta")
public class CarretaController {

    private final CarretaRepository carretaRepository;

    public CarretaController(CarretaRepository carretaRepository) {
        this.carretaRepository = carretaRepository;
    }

    @GetMapping
    public ResponseEntity<List<Carreta>> getAll() {
        List<Carreta> carretas = carretaRepository.findAll();
        return ResponseEntity.ok(carretas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carreta> getById(@PathVariable Long id) {
        return carretaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Carreta> create(@RequestBody Carreta carreta) {
        Carreta novaCarreta = carretaRepository.save(carreta);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaCarreta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody Carreta carreta) {
        int rowsUpdated = carretaRepository.update(id, carreta);
        if (rowsUpdated > 0) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        int rowsDeleted = carretaRepository.deleteById(id);
        if (rowsDeleted > 0) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}