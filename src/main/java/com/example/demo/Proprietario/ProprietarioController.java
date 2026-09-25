package com.example.demo.Proprietario;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proprietarios")
public class ProprietarioController {

    private final ProprietarioRepository repository;

    public ProprietarioController(ProprietarioRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Proprietario>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proprietario> getById(@PathVariable Integer id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Transactional(transactionManager = "secondaryTransactionManager")
    public ResponseEntity<?> create(@RequestBody Proprietario proprietario) {
        // Validação de campos obrigatórios
        if (proprietario.getNome() == null || proprietario.getNome().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O campo 'nome' é obrigatório.");
        }

        if (proprietario.getCpfCnpj() == null || proprietario.getCpfCnpj().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O campo 'cpfCnpj' é obrigatório.");
        }

        // Verifica prévia de duplicidade de CPF/CNPJ
        if (repository.existsByCpfCnpj(proprietario.getCpfCnpj())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Já existe um proprietário cadastrado com o CPF/CNPJ: " + proprietario.getCpfCnpj());
        }

        try {
            Proprietario criado = repository.save(proprietario);
            return ResponseEntity.status(HttpStatus.CREATED).body(criado);
        } catch (DuplicateKeyException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Erro: CPF/CNPJ já cadastrado no banco de dados.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao salvar proprietário: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Transactional(transactionManager = "secondaryTransactionManager")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody Proprietario proprietario) {
        // Validação de campos obrigatórios
        if (proprietario.getNome() == null || proprietario.getNome().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O campo 'nome' é obrigatório.");
        }

        if (proprietario.getCpfCnpj() == null || proprietario.getCpfCnpj().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O campo 'cpfCnpj' é obrigatório.");
        }

        // Verifica duplicidade excluindo o registro do próprio ID
        if (repository.existsByCpfCnpjAndIdNot(proprietario.getCpfCnpj(), id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("O CPF/CNPJ informado já pertence a outro proprietário.");
        }

        try {
            boolean atualizado = repository.update(id, proprietario);
            if (atualizado) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        } catch (DuplicateKeyException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Erro: CPF/CNPJ já cadastrado para outro registro.");
        }
    }

    @DeleteMapping("/{id}")
    @Transactional(transactionManager = "secondaryTransactionManager")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        boolean deletado = repository.deleteById(id);
        if (deletado) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}