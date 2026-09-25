package com.example.demo.Proprietario;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/proprietario")
public class ProprietariosController {

    @GetMapping("/teste")
    public ResponseEntity<String> listar() {
        return ResponseEntity.ok("Sua mensagem aqui");
    }
}