package com.practica.rest.controller.dao.services;

import com.practica.rest.controller.dao.GrafoDao;
import com.practica.rest.controller.tda.list.LinkedList;
import com.practica.modelos.Grafo;

public class GrafoServices {
    private GrafoDao obj;

    public GrafoServices() {
        obj = new GrafoDao();
    }

    public Boolean save(Grafo grafo) throws Exception {
        obj.setGrafo(grafo);
        return obj.save();
    }

    public Boolean update(Grafo grafo) throws Exception {
        return obj.update();
    }

    public Boolean delete(Integer id) throws Exception {
        // Llamamos al método delete del DAO
        return obj.delete(id);
    }

    public LinkedList<Grafo> listAll() {
        return obj.getListAll();
    }

    public Grafo getGrafo() {
        return obj.getGrafo();
    }

    public Grafo get(Integer id) throws Exception {
        return obj.get(id);
    }

    public void setGrafo(Grafo grafo) {
        obj.setGrafo(grafo);
    }
}