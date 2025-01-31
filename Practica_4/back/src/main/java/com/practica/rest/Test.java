package com.practica.rest;

import com.practica.rest.controller.tda.algoritmos.AlgoritmoBellmanFord;
import com.practica.rest.controller.tda.algoritmos.AlgoritmoFloyd;

public class Test {
    public static void main(String[] args) {
        int[][] graph10 = generateGraph(10);
        int[][] graph20 = generateGraph(20);
        int[][] graph30 = generateGraph(30);

        // Crear tabla para mostrar los tiempos
        System.out.println("Tiempos de ejecución de los algoritmos:");
        System.out.println("=======================================");
        System.out.println("| Algoritmo        | 10 datos | 20 datos | 30 datos |");
        System.out.println("-------------------------------------------------------");

        // Medir tiempo para Floyd-Warshall
        long startTime, endTime;
        
        // Para 10 datos
        startTime = System.nanoTime();
        AlgoritmoFloyd.floydWarshall(graph10);
        endTime = System.nanoTime();
        long timeFloyd10 = endTime - startTime;

        // Para 20 datos
        startTime = System.nanoTime();
        AlgoritmoFloyd.floydWarshall(graph20);
        endTime = System.nanoTime();
        long timeFloyd20 = endTime - startTime;

        // Para 30 datos
        startTime = System.nanoTime();
        AlgoritmoFloyd.floydWarshall(graph30);
        endTime = System.nanoTime();
        long timeFloyd30 = endTime - startTime;

        // Medir tiempo para Bellman-Ford
        // Para 10 datos
        startTime = System.nanoTime();
        AlgoritmoBellmanFord.bellmanFord(graph10, 0);
        endTime = System.nanoTime();
        long timeBellman10 = endTime - startTime;

        // Para 20 datos
        startTime = System.nanoTime();
        AlgoritmoBellmanFord.bellmanFord(graph20, 0);
        endTime = System.nanoTime();
        long timeBellman20 = endTime - startTime;

        // Para 30 datos
        startTime = System.nanoTime();
        AlgoritmoBellmanFord.bellmanFord(graph30, 0);
        endTime = System.nanoTime();
        long timeBellman30 = endTime - startTime;

        // Mostrar resultados en formato tabular
        System.out.println("| Floyd-Warshall   | " + timeFloyd10 + " ns | " + timeFloyd20 + " ns | " + timeFloyd30 + " ns |");
        System.out.println("| Bellman-Ford     | " + timeBellman10 + " ns | " + timeBellman20 + " ns | " + timeBellman30 + " ns |");
        System.out.println("=======================================");
    }

    // Método para generar gráficos aleatorios de diferentes tamaños
    public static int[][] generateGraph(int size) {
        int[][] graph = new int[size][size];
        // Inicializar el grafo con valores aleatorios
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i != j) {
                    graph[i][j] = (int) (Math.random() * 10) + 1; // Pesos aleatorios entre 1 y 10
                } else {
                    graph[i][j] = 0; // Sin bucles
                }
            }
        }
        return graph;
    }
}
