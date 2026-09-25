package com.example.demo.CavaloMecanico;


public class CavaloMecanico{

    private Integer id;
    private String placa;
    private String renavam;
    private String modelo;
    private String marca;
    private Integer anoFabricacao;
    private Integer idProprietario;

    public CavaloMecanico() {
    }

    public CavaloMecanico(Integer id, String placa, String renavam, String modelo, String marca, Integer anoFabricacao, Integer idProprietario) {
        this.id = id;
        this.placa = placa;
        this.renavam = renavam;
        this.modelo = modelo;
        this.marca = marca;
        this.anoFabricacao = anoFabricacao;
        this.idProprietario = idProprietario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Integer getAnoFabricacao() {
        return anoFabricacao;
    }

    public void setAnoFabricacao(Integer anoFabricacao) {
        this.anoFabricacao = anoFabricacao;
    }

    public Integer getIdProprietario() {
        return idProprietario;
    }

    public void setIdProprietario(Integer idProprietario) {
        this.idProprietario = idProprietario;
    }
}