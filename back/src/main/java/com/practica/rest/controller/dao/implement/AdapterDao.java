package com.practica.rest.controller.dao.implement;


import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.practica.rest.controller.tda.list.LinkedList;


public class AdapterDao<T> implements InterfazDao<T> {
    private Class<T> clazz;
    protected Gson g;
    public static String URL = "C:/Users/patri/OneDrive/Escritorio/Practica_4/back/data/grafos.json";

    public AdapterDao(Class clazz) {
        this.clazz = clazz;
        this.g = new Gson();
    }

    public T get(Integer id) throws Exception {
        LinkedList<T> list = listAll();
        if (!list.isEmpty()) {
            T[] matriz = list.toArray();
            for (int i = 0; i < matriz.length; i++) {
                if (getIdent(matriz[i]).intValue() == id.intValue()) {
                    return matriz[i];
                }
            }
        }
        return null;
    }

    private Integer getIdent(T obj) {
        try {
            Method method = null;
            for (Method m : clazz.getMethods()) {
                if (m.getName().equalsIgnoreCase("getId")) {
                    method = m;
                    break;
                }
            }
            if (method == null) {
                for (Method m : clazz.getSuperclass().getMethods()) {
                    if (m.getName().equalsIgnoreCase("getId")) {
                        method = m;
                        break;
                    }
                }
            }
            if (method != null)
                return (Integer) method.invoke(obj);
        } catch (Exception e) {
            // TODO: handle exception
            return -1;
        }
        return -1;
    }

    public LinkedList<T> listAll() {
        LinkedList<T> list = new LinkedList<>();
        try {
            String data = readFile();
            
            // Aquí se usa un TypeToken para deserializar el JSON en una LinkedList
            T[] matrix = g.fromJson(data, new com.google.gson.reflect.TypeToken<T[]>(){}.getType());
            
            // Convertir el array a la LinkedList personalizada
            for (T obj : matrix) {
                list.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    

    public void merge(T object, Integer index) throws Exception {
        LinkedList<T> list = listAll();
        list.update(object, index);
        String info = g.toJson(list.toArray());
        saveFile(info);
    }

    public void persist(T object) throws Exception {
        LinkedList<T> list = listAll();
        list.add(object);
        String info = g.toJson(list.toArray());
        saveFile(info);
    }

    public String readFile() throws Exception {
        // Leer directamente desde la URL sin concatenar
        Scanner in = new Scanner(new FileReader(URL));
        StringBuilder sb = new StringBuilder();
        while (in.hasNext()) {
            sb.append(in.next());
        }
        in.close();
        return sb.toString();
    }

    public void saveFile(String data) throws Exception {
        FileWriter f = new FileWriter(URL);
        f.write(data);
        f.flush();
        f.close();
    }
}


