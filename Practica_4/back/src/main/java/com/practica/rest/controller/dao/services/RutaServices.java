package com.practica.rest.controller.dao.services;

import com.practica.rest.controller.dao.RutaDao;
import com.practica.rest.controller.tda.list.LinkedList;
import com.practica.modelos.Ruta;

public class RutaServices {
    private RutaDao obj;

    public RutaServices() {
        obj = new RutaDao();
    }

    public Boolean save(Ruta ruta) throws Exception {
        obj.setRuta(ruta);
        return obj.save();
    }

    public Boolean update(Ruta ruta) throws Exception {
        return obj.update();
    }

    public Boolean delete(Integer id) throws Exception {
        // Llamamos al método delete del DAO
        return obj.delete(id);
    }

    public LinkedList<Ruta> listAll() {
        return obj.getListAll();
    }

    public Ruta getRuta() {
        return obj.getRuta();
    }

    public Ruta get(Integer id) throws Exception {
        return obj.get(id);
    }

    public void setRuta(Ruta ruta) {
        obj.setRuta(ruta);
    }
}