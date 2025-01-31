package com.practica.rest.controller.tda.list;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.practica.modelos.Parada;

import java.io.IOException;

public class LinkedListDeserializer extends JsonDeserializer<LinkedList<Parada>> {

    @Override
    public LinkedList<Parada> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        ArrayNode arrayNode = jp.getCodec().readTree(jp);
        LinkedList<Parada> list = new LinkedList<>();
        
        for (int i = 0; i < arrayNode.size(); i++) {
            // Convertir cada elemento en el JSON a un objeto Parada
            Parada parada = jp.getCodec().treeToValue(arrayNode.get(i), Parada.class);
            list.add(parada); // Agregarlo a la LinkedList
        }
        
        return list; // Devolver la lista completada
    }
}
