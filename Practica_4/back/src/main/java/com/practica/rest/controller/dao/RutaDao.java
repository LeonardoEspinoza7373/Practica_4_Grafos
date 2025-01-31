package com.practica.rest.controller.dao;

import com.practica.rest.controller.dao.implement.AdapterDao;
import com.practica.rest.controller.exception.ListEmptyException;
import com.practica.rest.controller.tda.list.LinkedList;
import com.practica.modelos.Ruta;

public class RutaDao extends AdapterDao<Ruta> {
    private Ruta ruta;
    private LinkedList<Ruta> listAll;

    public RutaDao() {
        super(Ruta.class);
    }

    public Ruta getRuta() {
        if (ruta == null) {
            ruta = new Ruta();
        }
        return this.ruta;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }

    public LinkedList<Ruta> getListAll() {
        if (listAll == null) {
            this.listAll = listAll(); 
        }
        return listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getListAll().getSize() + 1;
        getRuta().setId(id);
        this.persist(getRuta()); 
        this.listAll = listAll();
        return true;
    }

    public Boolean update() throws Exception {
        this.merge(getRuta(), getRuta().getId() - 1); 
        this.listAll = listAll();
        return true;
    }

    public Boolean delete(Integer idRuta) throws Exception {
        LinkedList<Ruta> rutaList = getListAll(); // Obtener la lista actual
        if (rutaList.isEmpty()) {
            throw new ListEmptyException("La lista de rutas está vacía.");
        }

        Integer indexToDelete = -1;
        for (int i = 0; i < rutaList.getSize(); i++) {
            if (rutaList.get(i).getId() == idRuta) {
                indexToDelete = i;
                break;
            }
        }

        if (indexToDelete == -1) {
            throw new Exception("La Ruta con el ID " + idRuta + " no fue encontrada.");
        }

        // Eliminar la ruta de la lista
        rutaList.remove(indexToDelete);

        // Guardar la lista actualizada en el archivo JSON
        String info = g.toJson(rutaList.toArray());
        saveFile(info);

        // Actualizar la lista en memoria
        this.listAll = rutaList;

        return true;
    }
}