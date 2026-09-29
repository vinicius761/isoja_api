package com.example.demo.Carreta;

import java.math.BigDecimal;

public class Carreta {

    private Long id;
    private String placa;
    private String renavam;
    private String tipo;
    private Integer eixos;
    private BigDecimal capacidadeCargaKg;
    private Integer anoFabricacao;
    private Long idProprietario;

    public Carreta() {
    }

    public Carreta(Long id, String placa, String renavam, String tipo, Integer eixos, BigDecimal capacidadeCargaKg, Integer anoFabricacao, Long idProprietario) {
        this.id = id;
        this.placa = placa;
        this.renavam = renavam;
        this.tipo = tipo;
        this.eixos = eixos;
        this.capacidadeCargaKg = capacidadeCargaKg;
        this.anoFabricacao = anoFabricacao;
        this.idProprietario = idProprietario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getRenavam() {
        return renavam;
    }

    public void setRenavam(String renavam) {
        this.renavam = renavam;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getEixos() {
        return eixos;
    }

    public void setEixos(Integer eixos) {
        this.eixos = eixos;
    }

    public BigDecimal getCapacidadeCargaKg() {
        return capacidadeCargaKg;
    }

    public void setCapacidadeCargaKg(BigDecimal capacidadeCargaKg) {
        this.capacidadeCargaKg = capacidadeCargaKg;
    }

    public Integer getAnoFabricacao() {
        return anoFabricacao;
    }

    public void setAnoFabricacao(Integer anoFabricacao) {
        this.anoFabricacao = anoFabricacao;
    }

    public Long getIdProprietario() {
        return idProprietario;
    }

    public void setIdProprietario(Long idProprietario) {
        this.idProprietario = idProprietario;
    }
}