package com.practica.modelos;

public class Ruta {
    private Integer id;
    private Parada origen;
    private Parada destino;
    private int distancia;

    public Ruta() {}

    public Ruta(Integer id, Parada origen, Parada destino, int distancia) {
        if (distancia < 0) throw new IllegalArgumentException("La distancia no puede ser negativa");
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.distancia = distancia;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Parada getOrigen() {
        return origen;
    }

    public void setOrigen(Parada origen) {
        this.origen = origen;
    }

    public Parada getDestino() {
        return destino;
    }

    public void setDestino(Parada destino) {
        this.destino = destino;
    }

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        if (distancia < 0) throw new IllegalArgumentException("La distancia no puede ser negativa");
        this.distancia = distancia;
    }
}

