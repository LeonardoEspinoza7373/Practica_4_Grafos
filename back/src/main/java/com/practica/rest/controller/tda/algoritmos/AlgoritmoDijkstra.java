package com.practica.rest.controller.tda.algoritmos;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import com.practica.rest.controller.exception.ListEmptyException;
import com.practica.rest.controller.tda.list.LinkedList;
import com.practica.modelos.Grafo;
import com.practica.modelos.Parada;
import com.practica.modelos.Ruta;

public class AlgoritmoDijkstra {

    public static class Nodo implements Comparable<Nodo> {
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

    public LinkedList<Parada> dijkstra(Grafo grafo, int origen, int destino) throws ListEmptyException {
        Map<Integer, Integer> distancias = new HashMap<>();
        Map<Integer, Integer> padres = new HashMap<>();
        PriorityQueue<Nodo> colaPrioridad = new PriorityQueue<>();
        LinkedList<Parada> paradas = grafo.getParadas();

        if (paradas == null || paradas.isEmpty()) {
            System.out.println("El grafo está vacío.");
            return new LinkedList<>();
        }

        // Inicializar distancias y padres
        for (int i = 0; i < paradas.getSize(); i++) {
            int id = paradas.get(i).getId();
            distancias.put(id, Integer.MAX_VALUE);
            padres.put(id, -1);
        }

        // La distancia al nodo origen es 0
        distancias.put(origen, 0);
        colaPrioridad.add(new Nodo(origen, 0));

        // Dijkstra
        while (!colaPrioridad.isEmpty()) {
            Nodo actual = colaPrioridad.poll();
            int nodoActual = actual.id;

            // Si llegamos al destino, terminar
            if (nodoActual == destino) break;

            // Obtener rutas del nodo actual
            LinkedList<Ruta> rutas = grafo.getRutas();
            for (int j = 0; j < rutas.getSize(); j++) {
                Ruta ruta = rutas.get(j);
                if (ruta.getOrigen().getId() == nodoActual) {
                    int vecino = ruta.getDestino().getId();
                    int nuevaDistancia = actual.distancia + ruta.getDistancia();

                    // Actualizar si encontramos una distancia menor
                    if (nuevaDistancia < distancias.get(vecino)) {
                        distancias.put(vecino, nuevaDistancia);
                        padres.put(vecino, nodoActual);
                        colaPrioridad.add(new Nodo(vecino, nuevaDistancia));
                    }
                }
            }
        }

        // Reconstruir el camino
        LinkedList<Parada> camino = new LinkedList<>();
        Integer current = destino;
        while (current != -1) {
            Parada parada = encontrarParadaPorId(paradas, current);
            if (parada != null) {
                camino.addFirst(parada);
                current = padres.get(current);
            } else {
                break;
            }
        }

        // Si el camino está vacío o no incluye el origen, no hay ruta
        if (camino.isEmpty() || camino.get(0).getId() != origen) {
            return new LinkedList<>();
        }

        return camino;
    }

    private Parada encontrarParadaPorId(LinkedList<Parada> paradas, int id) throws ListEmptyException {
        for (int i = 0; i < paradas.getSize(); i++) {
            Parada p = paradas.get(i);
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }
}