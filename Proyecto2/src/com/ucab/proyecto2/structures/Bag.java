package com.ucab.proyecto2.structures;

import java.util.ArrayList;
import java.util.List;

public class Bag<T> {
    
    private List<T> things;
    private List<T> registry;

    public Bag() {
        things = new ArrayList<T>();
        registry = new ArrayList<T>();
    }

    public void put(T o) {
        things.add(o);
        if (!registry.contains(o)) {
            registry.add(o);
        }
    }

    public void put(T o, int amount) {
        for (int i = 0; i < amount; i++) {
            things.add(o);
            if (!registry.contains(o)) {
                registry.add(o);
            }
        }
    }

    public T get() {
        if (things.size() == 0) return null;
        int index = (int) (Math.random() * (things.size() - 1));
        return things.remove(index);
    }

    public List<T> getThings() {
        return things;
    }

    public void setThings(List<T> things) {
        this.things = things;
    }

    public List<T> getRegistry() {
        return registry;
    }

}
