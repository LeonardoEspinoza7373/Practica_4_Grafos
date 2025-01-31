package com.practica.rest;

import com.practica.rest.controller.dao.services.ParadaServices;
import com.practica.modelos.Parada;
import java.util.HashMap;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;


@Path("parada")
public class ParadaApi {

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllParadas() {
        HashMap<String, Object> res = new HashMap<>();
        ParadaServices ps = new ParadaServices();
        res.put("msg", "Ok");
        res.put("data", ps.listAll().toArray());

        if (ps.listAll().isEmpty()) {
            res.put("data", new Object[] {});
        }

        return Response.ok(res).build();
    }

    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getParada(@PathParam("id") Integer id) {
        HashMap<String, Object> res = new HashMap<>();
        ParadaServices ps = new ParadaServices();

        try {
            ps.setParada(ps.get(id));
        } catch (Exception e) {
            e.printStackTrace();
        }

        res.put("msg", "Ok");
        res.put("data", ps.getParada());

        if (ps.getParada() == null || ps.getParada().getId() == 0) {
            res.put("msg", "No se encontró Parada con ese identificador");
            return Response.status(Status.NOT_FOUND).entity(res).build();
        }

        return Response.ok(res).build();
    }

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(Parada parada) {
        HashMap<String, Object> res = new HashMap<>();
        ParadaServices ps = new ParadaServices();

        try {
            ps.save(parada);
            res.put("msg", "Ok");
            res.put("data", "Parada guardada correctamente");
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
    public Response update(Parada parada) {
        HashMap<String, Object> res = new HashMap<>();
        ParadaServices ps = new ParadaServices();

        try {
            ps.update(parada);
            res.put("msg", "Ok");
            res.put("data", "Parada actualizada correctamente");
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
            ParadaServices ps = new ParadaServices();
            Boolean success = ps.delete(id);
            if (success) {
                res.put("msg", "Ok");
                res.put("data", "Parada eliminada correctamente");
                return Response.ok(res).build();
            } else {
                res.put("msg", "Error");
                res.put("data", "Parada no encontrada");
                return Response.status(Status.NOT_FOUND).entity(res).build();
            }
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }
}


