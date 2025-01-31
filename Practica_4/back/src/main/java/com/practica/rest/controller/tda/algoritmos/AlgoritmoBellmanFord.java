package com.practica.rest.controller.tda.algoritmos;

public class AlgoritmoBellmanFord {
    public static void bellmanFord(int[][] graph, int source) {
        int n = graph.length;
        int[] dist = new int[n];

        // Inicializar las distancias desde la fuente
        for (int i = 0; i < n; i++) {
            dist[i] = Integer.MAX_VALUE;
        }
        dist[source] = 0;

        // Relajar los bordes (n-1) veces
        for (int i = 1; i < n; i++) {
            for (int u = 0; u < n; u++) {
                for (int v = 0; v < n; v++) {
                    if (graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE && dist[u] + graph[u][v] < dist[v]) {
                        dist[v] = dist[u] + graph[u][v];
                    }
                }
            }
        }

        // Verificar ciclos negativos
        for (int u = 0; u < n; u++) {
            for (int v = 0; v < n; v++) {
                if (graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE && dist[u] + graph[u][v] < dist[v]) {
                    System.out.println("El grafo contiene un ciclo negativo.");
                    return;
                }
            }
        }

        // Mostrar las distancias mínimas desde el nodo fuente
        System.out.println("Distancias mínimas desde el nodo " + source + ":");
        for (int i = 0; i < n; i++) {
            if (dist[i] == Integer.MAX_VALUE) {
                System.out.println("INF");
            } else {
                System.out.println("Nodo " + i + ": " + dist[i]);
            }
        }
    }
}

