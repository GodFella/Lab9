/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab9.MyMap;

import static java.lang.Math.*;
import java.util.LinkedList;

/**
 *
 * @author serg
 */
public class MyHashMap implements MyMap {

    private int currentSize;
    private double loadFactor;
    private LinkedList[] array;

    //хэш-функция для добавления-удаления из массива
    private static class HashFunction {

        static int getValue(Object o, int length) {
            return abs(o.hashCode() % length);
        }
    }
    
    private class SimpleEntry implements MyMap.Entry {

        private Object value;
        private Object key;

        SimpleEntry(Object v, Object k) {
            value = v;
            key = k;
        }

        @Override
        public boolean equals(Object o) {
            if (o.equals(value)) {
                return true;
            }
            return false;
        }

        @Override
        public Object getKey() {
            return key;
        }

        @Override
        public Object getValue() {
            return value;
        }

        @Override
        public int hashCode() {
            return HashFunction.getValue(key, array.length);
        }

        @Override
        public void setValue(Object o) {
            value = o;
        }

        @Override
        public String toString() {
            StringBuilder s = new StringBuilder();
            s.append(key.toString());
            s.append(" ");
            s.append(value.toString());
            return s.toString();
        }
    }

    public MyHashMap() {
        array = new LinkedList[16];
        for (int i = 0; i < array.length; i++) {
            array[i] = new LinkedList();
        }
        loadFactor = 0.75;
    }

    public MyHashMap(int initialCapacity) throws IllegalArgumentException {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException();
        }
        array = new LinkedList[initialCapacity];
        for (int i = 0; i < array.length; i++) {
            array[i] = new LinkedList();
        }
        loadFactor = 0.75;
    }

    public MyHashMap(int initialCapacity, float loadFactor) throws IllegalArgumentException {
        if (initialCapacity < 0 || loadFactor <= 0) {
            throw new IllegalArgumentException();
        }
        array = new LinkedList[initialCapacity];
        for (int i = 0; i < array.length; i++) {
            array[i] = new LinkedList();
        }
        this.loadFactor = loadFactor;
    }
    
    //так как мой хэш мэп реализуется массивом,который в каждой ячейке хранит линкед лист для решения коллизий
    //,то эта функция реформатирует всю структуру данных для более разумного хранения данных
    // при увеличении размерности массива 
 
    private void reformate() {
        LinkedList[] help = array;
        array = new LinkedList[help.length * 2];
        for (int i = 0; i < array.length; i++) {
            array[i] = new LinkedList();
        }
        SimpleEntry current;
        for (int i = 0; i < help.length; i++) {
            while (help[i].size() > 0) {
                current = (SimpleEntry) help[i].pollFirst();
                array[current.hashCode()].addFirst(current);
            }
        }
    }

    @Override
    public void put(Object key, Object value) {
        int num = HashFunction.getValue(key, array.length);
        for (int i = 0; i < array[num].size(); i++) {
            if (((SimpleEntry) array[num].get(i)).getKey().equals(key)) {
                ((SimpleEntry) array[num].get(i)).setValue(value);
                return;
            }
        }
        SimpleEntry current = new SimpleEntry(value, key);
        array[current.hashCode()].add(current);
        currentSize++;
        if ((double) currentSize / array.length > loadFactor) {
            reformate();
        }
    }

    @Override
    public Object get(Object key) {
        int num = HashFunction.getValue(key, array.length);
        for (int i = 0; i < array[num].size(); i++) {
            if (((SimpleEntry) array[num].get(i)).getKey().equals(key)) {
                return ((SimpleEntry) array[num].get(i)).getValue();
            }
        }
        return null;
    }

    @Override
    public void clear() {
        for (int i = 0; i < array.length; i++) {
            array[i].clear();
        }
        currentSize = 0;
    }

    @Override
    public boolean containsKey(Object key) {
        int num = HashFunction.getValue(key, array.length);
        for (int i = 0; i < array[num].size(); i++) {
            if (((SimpleEntry) array[num].get(i)).getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].size(); j++) {
                if (((SimpleEntry) array[i].get(j)).getValue().equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        if (currentSize == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Object remove(Object key) {
        int num = HashFunction.getValue(key, array.length);
        for (int i = 0; i < array[num].size(); i++) {
            if (((SimpleEntry) array[num].get(i)).getKey().equals(key)) {
                currentSize--;
                return array[num].remove(i);
            }
        }
        return null;
    }

    @Override
    public int size(){
        return currentSize;
    }
    
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Current size= ");
        s.append(currentSize);
        s.append("\n");
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < ((LinkedList) array[i]).size(); j++) {
                s.append(((SimpleEntry) array[i].get(j)).toString());
                s.append("\n");
            }
        }
        return s.toString();
    }
}
