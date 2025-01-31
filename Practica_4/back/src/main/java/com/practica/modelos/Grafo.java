package com.practica.modelos;

import com.practica.rest.controller.tda.algoritmos.AlgoritmoBellmanFord;
import com.practica.rest.controller.tda.algoritmos.AlgoritmoDijkstra;
import com.practica.rest.controller.tda.algoritmos.AlgoritmoFloyd;
import com.practica.rest.controller.tda.list.LinkedList;

import com.practica.rest.controller.exception.ListEmptyException;


public class Grafo {
    private Integer id;
    private String nombre; 
    private String descripcion;
    private LinkedList<Parada> paradas;
    private LinkedList<Ruta> rutas;
    private AlgoritmoDijkstra dijkstraAlgoritmo;
    private AlgoritmoFloyd floydWarshallAlgoritmo;
     private AlgoritmoBellmanFord bellmanFordAlgoritmo;

    public Grafo() {
        this.paradas = new LinkedList<>();
        this.rutas = new LinkedList<>();
        this.dijkstraAlgoritmo = new AlgoritmoDijkstra();
        this.floydWarshallAlgoritmo = new AlgoritmoFloyd();
        this.bellmanFordAlgoritmo = new AlgoritmoBellmanFord();
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LinkedList<Parada> getParadas() {
        return paradas;
    }

    public void setParadas(LinkedList<Parada> paradas) {
        this.paradas = paradas;
    }

    public LinkedList<Ruta> getRutas() {
        return rutas;
    }

    public void setRutas(LinkedList<Ruta> rutas) {
        this.rutas = rutas;
    }

    
  
}
