package com.practica.rest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practica.modelos.Grafo;
import com.practica.modelos.Parada;
import com.practica.rest.controller.dao.services.GrafoServices;
import com.practica.rest.controller.tda.list.LinkedList;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("grafo")
public class GrafoApi {

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllGrafos() {
        HashMap<String, Object> res = new HashMap<>();
        GrafoServices gs = new GrafoServices();
        res.put("msg", "Ok");
        res.put("data", gs.listAll().toArray());

        if (gs.listAll().isEmpty()) {
            res.put("data", new Object[] {});
        }

        return Response.ok(res).build();
    }

    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGrafo(@PathParam("id") Integer id) {
        HashMap<String, Object> res = new HashMap<>();
        GrafoServices gs = new GrafoServices();

        try {
            gs.setGrafo(gs.get(id));
        } catch (Exception e) {
            e.printStackTrace();
        }

        res.put("msg", "Ok");
        res.put("data", gs.getGrafo());

        if (gs.getGrafo() == null || gs.getGrafo().getId() == 0) {
            res.put("msg", "No se encontró Grafo con ese identificador");
            return Response.status(Status.NOT_FOUND).entity(res).build();
        }

        return Response.ok(res).build();
    }

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(Grafo grafo) {
        HashMap<String, Object> res = new HashMap<>();
        GrafoServices gs = new GrafoServices();

        try {
            gs.save(grafo);
            res.put("msg", "Ok");
            res.put("data", "Grafo guardado correctamente");
            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(Grafo grafo) {
        HashMap<String, Object> res = new HashMap<>();
        GrafoServices gs = new GrafoServices();

        try {
            gs.update(grafo);
            res.put("msg", "Ok");
            res.put("data", "Grafo actualizado correctamente");
            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/delete/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") Integer id) {
        HashMap<String, Object> res = new HashMap<>();

        try {
            GrafoServices gs = new GrafoServices();
            Boolean success = gs.delete(id);

            if (success) {
                res.put("msg", "Ok");
                res.put("data", "Grafo eliminado correctamente");
                return Response.ok(res).build();
            } else {
                res.put("msg", "Error");
                res.put("data", "Grafo no encontrado");
                return Response.status(Status.NOT_FOUND).entity(res).build();
            }
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

 
/* 
    @GET
    @Path("/dijkstra/{origen}/{destino}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerCaminoDijkstra(
        @PathParam("origen") int origen, 
        @PathParam("destino") int destino
    ) {
        try {
            System.out.println("🔴 Llamando a Dijkstra: " + origen + " → " + destino);
            Grafo grafo = obtenerGrafo();
    
            if (grafo == null) {
                System.err.println("❌ Grafo no cargado (es nulo)");
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Grafo no encontrado").build();
            }
    
            System.out.println("✅ Grafo cargado. Paradas: " + grafo.getParadas().getSize());
    
            LinkedList<Parada> camino = grafo.dijkstra(origen, destino);
    
            if (camino == null || camino.isEmpty()) {
                System.err.println("⚠️ No hay camino entre " + origen + " y " + destino);
                return Response.status(Response.Status.NOT_FOUND)
                    .entity("No existe ruta").build();
            }
    
            System.out.println("Camino encontrado: " + camino);
            return Response.ok(camino).build();
        } catch (Exception e) {
            e.printStackTrace(); // 👈 Esto mostrará el error en la consola
            return Response.serverError()
                .entity("Error interno: " + e.getMessage()).build();
        }
    }
*/
    private Grafo obtenerGrafo() {
    String filePath = "C:/Users/patri/OneDrive/Escritorio/Practica_4/back/data/grafos.json";
    try {
        System.out.println("📂 Leyendo JSON desde: " + filePath);
        String json = new String(Files.readAllBytes(Paths.get(filePath)));
        ObjectMapper mapper = new ObjectMapper();

        // Configurar Jackson para ignorar campos desconocidos
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        List<Grafo> grafos = mapper.readValue(json, new TypeReference<List<Grafo>>() {});
        
        if (grafos == null || grafos.isEmpty()) {
            System.err.println("❌ No hay grafos en el archivo JSON");
            return null;
        }

        Grafo grafo = grafos.get(0);
        System.out.println("✅ Grafo cargado: " + grafo.getNombre());
        return grafo;
    } catch (IOException e) {
        System.err.println("❌ Error al cargar el JSON: " + e.getMessage());
        return null;
    }
}
        
    
}
