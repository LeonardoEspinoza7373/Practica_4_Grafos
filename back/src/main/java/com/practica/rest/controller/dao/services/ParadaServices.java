package com.practica.rest.controller.dao.services;

import com.practica.rest.controller.dao.ParadaDao;
import com.practica.rest.controller.tda.list.LinkedList;
import com.practica.modelos.Parada;

public class ParadaServices {
    private ParadaDao obj;

    public ParadaServices() {
        obj = new ParadaDao();
    }

    public Boolean save(Parada parada) throws Exception {
        obj.setParada(parada);
        return obj.save();
    }

    public Boolean update(Parada parada) throws Exception {
        return obj.update();
    }

    public Boolean delete(Integer id) throws Exception {
        // Llamamos al método delete del DAO
        return obj.delete(id);
    }

    public LinkedList<Parada> listAll() {
        return obj.getListAll();
    }

    public Parada getParada() {
        return obj.getParada();
    }

    public Parada get(Integer id) throws Exception {
        return obj.get(id);
    }

    public void setParada(Parada parada) {
        obj.setParada(parada);
    }
}