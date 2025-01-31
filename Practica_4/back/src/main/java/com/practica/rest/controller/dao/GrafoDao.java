package com.practica.rest.controller.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import com.practica.rest.controller.dao.implement.AdapterDao;
import com.practica.rest.controller.exception.ListEmptyException;
import com.practica.rest.controller.tda.list.LinkedList;
import com.practica.modelos.Grafo;
import com.practica.modelos.Parada;
import com.practica.modelos.Ruta;

public class GrafoDao extends AdapterDao<Grafo> {
    private Grafo grafo;
    private LinkedList<Grafo> listAll;

    public GrafoDao() {
        super(Grafo.class);
    }

    public Grafo getGrafo() {
        if (grafo == null) {
            grafo = new Grafo();
        }
        return this.grafo;
    }

    public void setGrafo(Grafo grafo) {
        this.grafo = grafo;
    }

    public LinkedList<Grafo> getListAll() {
        if (listAll == null) {
            this.listAll = listAll();
        }
        return listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getListAll().getSize() + 1;
        getGrafo().setId(id);
        this.persist(getGrafo());
        this.listAll = listAll();
        return true;
    }

    public Boolean update() throws Exception {
        this.merge(getGrafo(), getGrafo().getId() - 1);
        this.listAll = listAll();
        return true;
    }

    public Boolean delete(Integer idGrafo) throws Exception {
        LinkedList<Grafo> grafo = getListAll(); // Obtener la lista actual
        if (grafo.isEmpty()) {
            throw new ListEmptyException("La lista está vacía.");
        }

        // Buscar el índice del grafo en la lista
        Integer indexToDelete = -1;
        for (int i = 0; i < grafo.getSize(); i++) {
            if (grafo.get(i).getId() == idGrafo) {
                indexToDelete = i;
                break;
            }
        }

        if (indexToDelete == -1) {
            throw new Exception("El Grafo con el ID " + idGrafo + " no fue encontrado.");
        }

        // Eliminar el grafo de la lista
        grafo.remove(indexToDelete);

        // Guardar la lista actualizada en el archivo JSON
        String info = g.toJson(grafo.toArray());
        saveFile(info);

        // Actualizar la lista en memoria
        this.listAll = grafo;

        return true;
    }

    public Map<Integer, Integer> dijkstra(int origen) {
        Map<Integer, Integer> distancias = new HashMap<>();
        Map<Integer, Integer> padres = new HashMap<>();
        PriorityQueue<Nodo> colaPrioridad = new PriorityQueue<>();
        LinkedList<Parada> paradas = grafo.getParadas();
    
        // Verificar si la lista de paradas está vacía
        if (paradas == null || paradas.getSize() == 0) {
            System.out.println("El grafo está vacío.");
            return distancias;
        }
    
        // Inicializar distancias y padres
        try {
            for (int i = 0; i < paradas.getSize(); i++) {
                int id = paradas.get(i).getId();
                distancias.put(id, Integer.MAX_VALUE);
                padres.put(id, -1);
            }
        } catch (ListEmptyException e) {
            System.out.println("La lista de paradas está vacía.");
            return distancias;
        }
    
        // La distancia al nodo origen es 0
        distancias.put(origen, 0);
        colaPrioridad.add(new Nodo(origen, 0));
    
        // Dijkstra
        while (!colaPrioridad.isEmpty()) {
            Nodo actual = colaPrioridad.poll();
            int nodoActual = actual.id;
            int distanciaActual = actual.distancia;
    
            // Obtener rutas del nodo actual
            LinkedList<Ruta> rutas = grafo.getRutas();
    
            // Manejar la excepción al acceder a rutas
            try {
                for (int j = 0; j < rutas.getSize(); j++) {
                    Ruta ruta = rutas.get(j);
                    if (ruta.getOrigen().getId() == nodoActual) {
                        int vecino = ruta.getDestino().getId();
                        int nuevaDistancia = distanciaActual + ruta.getDistancia();
    
                        // Solo actualizar si encontramos una distancia menor
                        if (nuevaDistancia < distancias.getOrDefault(vecino, Integer.MAX_VALUE)) {
                            distancias.put(vecino, nuevaDistancia);
                            padres.put(vecino, nodoActual);
                            colaPrioridad.add(new Nodo(vecino, nuevaDistancia));
                        }
                    }
                }
            } catch (ListEmptyException e) {
                System.out.println("La lista de rutas está vacía.");
            }
        }
    
        return distancias;
    }
    

    // Método Floyd-Warshall - Obtener las distancias más cortas entre todos los nodos
    public int[][] floydWarshall(int[][] grafo) {
        int numNodos = grafo.length;
        int[][] distancia = new int[numNodos][numNodos];

        // Inicializar la matriz de distancias con los valores del grafo
        for (int i = 0; i < numNodos; i++) {
            for (int j = 0; j < numNodos; j++) {
                if (i == j) {
                    distancia[i][j] = 0;
                } else {
                    distancia[i][j] = grafo[i][j] == 0 ? Integer.MAX_VALUE : grafo[i][j];
                }
            }
        }

        // Aplicar el algoritmo de Floyd-Warshall
        for (int k = 0; k < numNodos; k++) {
            for (int i = 0; i < numNodos; i++) {
                for (int j = 0; j < numNodos; j++) {
                    if (distancia[i][k] != Integer.MAX_VALUE && distancia[k][j] != Integer.MAX_VALUE &&
                        distancia[i][k] + distancia[k][j] < distancia[i][j]) {
                        distancia[i][j] = distancia[i][k] + distancia[k][j];
                    }
                }
            }
        }

        return distancia;
    }

    // Clase Nodo utilizada en Dijkstra
    private static class Nodo implements Comparable<Nodo> {
        int id, distancia;

        public Nodo(int id, int distancia) {
            this.id = id;
            this.distancia = distancia;
        }

        @Override
        public int compareTo(Nodo otro) {
            return Integer.compare(this.distancia, otro.distancia);
        }
    }
}
