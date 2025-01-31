package com.practica.rest;

import com.practica.rest.controller.dao.services.RutaServices;
import com.practica.modelos.Ruta;
import java.util.HashMap;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;


@Path("ruta")
public class RutaApi {

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRutas() {
        HashMap<String, Object> res = new HashMap<>();
        RutaServices rs = new RutaServices();
        res.put("msg", "Ok");
        res.put("data", rs.listAll().toArray());

        if (rs.listAll().isEmpty()) {
            res.put("data", new Object[] {});
        }

        return Response.ok(res).build();
    }

    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRuta(@PathParam("id") Integer id) {
        HashMap<String, Object> res = new HashMap<>();
        RutaServices rs = new RutaServices();

        try {
            rs.setRuta(rs.get(id));
        } catch (Exception e) {
            e.printStackTrace();
        }

        res.put("msg", "Ok");
        res.put("data", rs.getRuta());

        if (rs.getRuta() == null || rs.getRuta().getId() == 0) {
            res.put("msg", "No se encontró Ruta con ese identificador");
            return Response.status(Status.NOT_FOUND).entity(res).build();
        }

        return Response.ok(res).build();
    }

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(Ruta ruta) {
        HashMap<String, Object> res = new HashMap<>();
        RutaServices rs = new RutaServices();

        try {
            rs.save(ruta);
            res.put("msg", "Ok");
            res.put("data", "Ruta guardada correctamente");
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
    public Response update(Ruta ruta) {
        HashMap<String, Object> res = new HashMap<>();
        RutaServices rs = new RutaServices();

        try {
            rs.update(ruta);
            res.put("msg", "Ok");
            res.put("data", "Ruta actualizada correctamente");
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
            RutaServices rs = new RutaServices();
            Boolean success = rs.delete(id);
            if (success) {
                res.put("msg", "Ok");
                res.put("data", "Ruta eliminada correctamente");
                return Response.ok(res).build();
            } else {
                res.put("msg", "Error");
                res.put("data", "Ruta no encontrada");
                return Response.status(Status.NOT_FOUND).entity(res).build();
            }
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }
}


