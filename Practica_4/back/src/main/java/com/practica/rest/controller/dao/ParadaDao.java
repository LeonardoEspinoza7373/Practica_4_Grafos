package com.practica.rest.controller.dao;

import com.practica.rest.controller.dao.implement.AdapterDao;
import com.practica.rest.controller.exception.ListEmptyException;
import com.practica.rest.controller.tda.list.LinkedList;
import com.practica.modelos.Parada;

public class ParadaDao extends AdapterDao<Parada> {
    private Parada parada;
    private LinkedList<Parada> listAll;

    public ParadaDao() {
        super(Parada.class);
    }

    public Parada getParada() {
        if (parada == null) {
            parada = new Parada();
        }
        return this.parada;
    }

    public void setParada(Parada parada) {
        this.parada = parada;
    }

    public LinkedList<Parada> getListAll() {
        if (listAll == null) {
            this.listAll = listAll(); 
        }
        return listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getListAll().getSize() + 1;
        getParada().setId(id);
        this.persist(getParada()); 
        this.listAll = listAll();
        return true;
    }

    public Boolean update() throws Exception {
        this.merge(getParada(), getParada().getId() - 1); 
        this.listAll = listAll();
        return true;
    }

    public Boolean delete(Integer idParada) throws Exception {
        LinkedList<Parada> paradaList = getListAll(); // Obtener la lista actual
        if (paradaList.isEmpty()) {
            throw new ListEmptyException("La lista de paradas está vacía.");
        }

        Integer indexToDelete = -1;
        for (int i = 0; i < paradaList.getSize(); i++) {
            if (paradaList.get(i).getId() == idParada) {
                indexToDelete = i;
                break;
            }
        }

        if (indexToDelete == -1) {
            throw new Exception("La Parada con el ID " + idParada + " no fue encontrada.");
        }

        // Eliminar la parada de la lista
        paradaList.remove(indexToDelete);

        // Guardar la lista actualizada en el archivo JSON
        String info = g.toJson(paradaList.toArray());
        saveFile(info);

        // Actualizar la lista en memoria
        this.listAll = paradaList;

        return true;
    }
}

