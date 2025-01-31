package com.practica.rest.controller.tda.list;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.practica.rest.controller.exception.ListEmptyException;
import com.practica.modelos.Parada;

/**
 
 **/
@JsonDeserialize(using = LinkedListDeserializer.class)
public class LinkedList<E> {
    private Node<E> header;
    private Node<E> last;
    private Integer size;
    public static Integer ASC = 1;
    public static Integer DESC = 0;

    /**
     * Clase que permite realizar una lista enlazada
     **/

  
    public LinkedList() {
        this.header = null;
        this.last = null;
        this.size = 0;
    }

    public Boolean isEmpty() {
        return (this.header == null || this.size == 0);
    }

    private void addHeader(E dato) {
        Node<E> help;
        if (isEmpty()) {
            help = new Node<>(dato);
            header = help;
            this.last = help;
        } else {
            Node<E> healpHeader = this.header;
            help = new Node<>(dato, healpHeader);
            this.header = help;

            // this.size++;
        }
        this.size++;
    }

    public void addFirst(E value) {
        Node<E> newNode = new Node<>(value);
        if (header == null) {
            header = newNode;
            last = newNode;
        } else {
            newNode.setNext(header);
            header = newNode;
        }
        size++;
    }

    private void addLast(E info) {
        Node<E> help;
        if (isEmpty()) {
            addHeader(info);
        } else {
            help = new Node<>(info, null);
            last.setNext(help);
            last = help;
            this.size++;
        }
    }

    public void add(E info) {
        addLast(info);
    }

    // ********** GET */
    private Node<E> getNode(Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, List empty");
        } else if (index.intValue() < 0 || index.intValue() >= this.size) {
            throw new IndexOutOfBoundsException("Error, fuera de rango");
        } else if (index.intValue() == 0) {
            return header;
        } else if (index.intValue() == (this.size - 1)) {
            return last;
        } else {
            Node<E> search = header;
            int cont = 0;
            while (cont < index.intValue()) {
                cont++;
                search = search.getNext();
            }
            return search;
        }
    }

    public void addAll(LinkedList<E> otraLista) {
        // Asegúrate de que otraLista no esté vacía
        if (otraLista != null && !otraLista.isEmpty()) {
            Node<E> actual = otraLista.header; // Empezamos desde el primer nodo de otraLista
            while (actual != null) {
                this.add(actual.getInfo()); // Usamos el método add para agregar el valor
                actual = actual.getNext(); // Avanzamos al siguiente nodo
            }
        }
    }
    

    private E getFirst() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, Lista vacia");
        }
        return header.getInfo();
    }

    private E getLast() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, Lista vacia");
        }
        return last.getInfo();
    }

    public E get(Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, List empty");
        } else if (index.intValue() < 0 || index.intValue() >= this.size.intValue()) {
            throw new IndexOutOfBoundsException("Error, fuera de rango");
        } else if (index.intValue() == 0) {
            return header.getInfo();
        } else if (index.intValue() == (this.size - 1)) {
            return last.getInfo();
        } else {
            Node<E> search = header;
            int cont = 0;
            while (cont < index.intValue()) {
                cont++;
                search = search.getNext();
            }
            return search.getInfo();
        }

    }

    /********* END GET */
    /*** ADD BY POSITION */
    public void add(E info, Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty() || index.intValue() == 0) {
            addHeader(info);
        } else if (index.intValue() == this.size.intValue()) {
            addLast(info);
        } else {

            Node<E> search_preview = getNode(index - 1);
            Node<E> search = getNode(index);
            Node<E> help = new Node<>(info, search);
            search_preview.setNext(help);
            this.size++;
        }
    }

    /*** END BY POSITION */
    public void reset() {
        this.header = null;
        this.last = null;
        this.size = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("List Data \n");
        try {

            Node<E> help = header;
            while (help != null) {
                sb.append(help.getInfo()).append(" -> ");
                help = help.getNext();
            }
        } catch (Exception e) {
            sb.append(e.getMessage());
        }

        return sb.toString();
    }

    public Integer getSize() {
        return this.size;
    }

    public E[] toArray() {
        E[] matrix = null;
        if (!isEmpty()) {
            Class clazz = header.getInfo().getClass();
            matrix = (E[]) java.lang.reflect.Array.newInstance(clazz, size);
            Node<E> aux = header;
            for (int i = 0; i < this.size; i++) {
                matrix[i] = aux.getInfo();
                aux = aux.getNext();
            }
        }
        return matrix;
    }

    public LinkedList<E> toList(E[] matrix) {
        reset();
        for (int i = 0; i < matrix.length; i++) {
            this.add(matrix[i]);
        }
        return this;
    }

    public void update(E data, Integer post) throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, la lista esta vacia");
        } else if (post < 0 || post >= size) {
            throw new IndexOutOfBoundsException("Error, esta fuera de los limites de la lista");
        } else if (post == 0) {
            header.setInfo(data);
        } else if (post == (size - 1)) {
            last.setInfo(data);
        } else {
            // 2 5 6 9 --> 2
            Node<E> search = header;
            Integer cont = 0;
            while (cont < post) {
                cont++;
                search = search.getNext();
            }
            search.setInfo(data);
        }
    }

    public E removeFirst() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Lista vacia");
        } else {
            E element = header.getInfo();
            Node<E> aux = header.getNext();
            header = aux;
            if (size.intValue() == 1) {
                last = null;
            }
            size--;
            return element;
        }
    }

    public E removeLast() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Lista vacia");
        } else {
            E element = last.getInfo();
            Node<E> aux = getNode(size - 2);
            if (aux == null) {
                last = null;
                if (size == 2) {
                    last = header;
                } else {
                    header = null;
                }
            } else {
                last = null;
                last = aux;
                last.setNext(null);
            }
            size--;
            return element;
        }
    }

    public E remove(Integer post) throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, la lista está vacía");
        } 
        if (post < 0 || post >= size) {
            throw new IndexOutOfBoundsException("Error, esta fuera de los límites de la lista");
        } 
        if (post == 0) {
            return removeFirst(); // Eliminar el primer nodo
        } 
        if (post == (size - 1)) {
            return removeLast(); // Eliminar el último nodo
        } 
    
        // Obtener el nodo anterior y el nodo actual
        Node<E> preview = getNode(post - 1);
        Node<E> actually = getNode(post);
        E element = actually.getInfo(); // Guardar el elemento a eliminar
    
        // Reconectar el nodo previo con el siguiente
        preview.setNext(actually.getNext());
    
        // Reducir el tamaño de la lista
        size--;
    
        // Retornar el elemento eliminado
        return element;
    }

}