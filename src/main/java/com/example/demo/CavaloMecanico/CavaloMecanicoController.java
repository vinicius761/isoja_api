package com.example.demo.CavaloMecanico;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cavalosmecanico")
public class CavaloMecanicoController {

    private final CavaloRepository cavaloRepository;

    public CavaloMecanicoController(CavaloRepository cavaloRepository) {
        this.cavaloRepository = cavaloRepository;
    }

    @GetMapping
    public ResponseEntity<List<CavaloMecanico>> getAll() {
        List<CavaloMecanico> cavalos = cavaloRepository.findAll();
        return ResponseEntity.ok(cavalos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CavaloMecanico> getById(@PathVariable Integer id) {
        return cavaloRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CavaloMecanico> create(@RequestBody CavaloMecanico cavalo) {
        CavaloMecanico novoCavalo = cavaloRepository.save(cavalo);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCavalo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody CavaloMecanico cavalo) {
        boolean updated = cavaloRepository.update(id, cavalo);
        if (updated) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        boolean deleted = cavaloRepository.deleteById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}