package org.example.generics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MutableStack<E> {
    private final List<E> list = new ArrayList<>();
    public MutableStack (E... items) {
        list.addAll(Arrays.asList(items));
    }

    public void push(E element) { list.add(element); }
    public E peek() { return list.get(list.size()-1); }
    public E pop() { return list.remove(list.size()-1); }
    public boolean isEmpty() { return list.isEmpty(); }
    public int size() { return list.size(); }

    public String toString() {
        return ""+this.list;
    }
}
