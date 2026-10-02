package com.example.demo.Acoplamento;

import java.time.LocalDateTime;

public class Acoplamento {

    private Long id;
    private Long idCavalo;
    private Long idCarreta;
    private String tag; 
    private LocalDateTime dataEngate;
    private LocalDateTime dataDesengate;

    public Acoplamento() {
    }

    public Acoplamento(Long id, Long idCavalo, Long idCarreta, String tag, LocalDateTime dataEngate, LocalDateTime dataDesengate) {
        this.id = id;
        this.idCavalo = idCavalo;
        this.idCarreta = idCarreta;
        this.tag = tag;
        this.dataEngate = dataEngate;
        this.dataDesengate = dataDesengate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCavalo() {
        return idCavalo;
    }

    public void setIdCavalo(Long idCavalo) {
        this.idCavalo = idCavalo;
    }

    public Long getIdCarreta() {
        return idCarreta;
    }

    public void setIdCarreta(Long idCarreta) {
        this.idCarreta = idCarreta;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public LocalDateTime getDataEngate() {
        return dataEngate;
    }

    public void setDataEngate(LocalDateTime dataEngate) {
        this.dataEngate = dataEngate;
    }

    public LocalDateTime getDataDesengate() {
        return dataDesengate;
    }

    public void setDataDesengate(LocalDateTime dataDesengate) {
        this.dataDesengate = dataDesengate;
    }
}