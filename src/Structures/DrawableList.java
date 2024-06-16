package Structures;

import Features.Components.Drawable;

import java.awt.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class DrawableList<T> implements Comparable<DrawableList<T>> {
    int layer;
    LinkedList<T> list = new LinkedList<>();
    LinkedList<DrawableList<T>> supportList = new LinkedList<>();

    public DrawableList(int layer) {
        this.layer = layer;
    }

    public synchronized boolean isEmpty() {
        return list.isEmpty() && supportList.isEmpty();
    }

    public synchronized boolean add(T t) {
        if(t == null)
            return false;
        list.add(t);
        return true;
    }

    public synchronized boolean remove(T t) {
        if (list.contains(t)) {
            list.remove(t);
            return true;
        }
        return false;
    }

    public synchronized boolean contains(T t) {
        return list.contains(t) || search(t);
    }

    private synchronized boolean search(T t) {
        for (DrawableList<T> list : supportList) {
            if (list.contains(t)) {
                return true;
            }
        }
        return false;
    }

    public synchronized boolean addList(DrawableList<T> list) {
        if(list == null || list.isEmpty())
            return false;
        if (!supportList.contains(list)) {
            supportList.addLast(list);
            return true;
        }
        return false;
    }

    public synchronized boolean removeList(DrawableList<T> list) {
        if (list == null)
            return false;
        supportList.remove(list);
        return true;
    }

    public synchronized int size() {
        int size = list.size();
        for (DrawableList<T> list : supportList) {
            size += list.size();
        }
        return size;
    }

    public synchronized void draw(Graphics2D g) {
        for(T t : list) {
            ((Drawable)t).draw(g);
        }
        for (DrawableList<T> list : supportList) {
            list.draw(g);
        }
    }

    @Override
    public String toString() {
        return list.toString()+"\n"+supportList.toString();
    }

    @Override
    public synchronized int compareTo(DrawableList<T> o) {
        return this.layer - o.layer;
    }

    public synchronized void sort() {
        ((LinkedList<Drawable>) list).sort(Comparator.comparingInt(Drawable::getLayer));
        Collections.sort(supportList);
    }
}