/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab9.MyMap;

import java.lang.Comparable;
import java.util.Comparator;
import static java.lang.Math.*;
import java.util.LinkedList;

/**
 *
 * @author serg
 */
public class MyTreeMap implements MyMap {
    //перечесление, созданное для определения цвета соеденения вершин в
    //красно-черном бинароном дереве
    enum Color {

        RED(true), BLACK(false);
        private boolean value;

        private Color(boolean value) {
            this.value = value;
        }

        boolean getValue() {
            return this.value;
        }
    }

    private class SimpleEntry implements MyMap.Entry, Comparable {

        SimpleEntry left;
        SimpleEntry right;
        private Object key;
        private Object value;
        Color linkColor;

        SimpleEntry(Object key, Object value, Color linkColor) {
            this.key = key;
            this.value = value;
            this.linkColor = linkColor;
        }

        @Override
        public void setValue(Object o) {
            this.value = o;
        }

        @Override
        public Object getValue() {
            return this.value;
        }

        @Override
        public Object getKey() {
            return this.key;
        }

        @Override
        //если компаратор не присылается(comp=null), то используется естественное
        //упорядочивание ключей(compareTo), иначе используется компаратор, который вставляется
        //в compareTo
        public int compareTo(Object key) {
            if (comp == null) {
                return ((String) this.key).compareTo((String) key);
            }
            return comp.compare((String) key, (String) key);
        }

        @Override
                public String toString(){
                    StringBuilder s=new StringBuilder();
                    s.append(this.key);
                    s.append(" - ");
                    s.append(this.value);
                    s.append("\n");
                    return s.toString();
                }
        
        boolean isRed() {
            return this.linkColor.getValue();
        }
    }
    private SimpleEntry root;
    private Comparator comp;
    private int size;
    //сохраняет текущий удаленный элемент
    private SimpleEntry currentDeletion;
    //сохраняет текущую информацию про содержание в моем три мэп какого-то значения
    private boolean flag;
    
    public MyTreeMap() {

    }

    public MyTreeMap(Comparator comp) {
        this.comp = comp;
    }
    //функция, которая поварачивает красное соединение справа-налево
    private SimpleEntry rotateLeft(SimpleEntry node) {
        SimpleEntry x = node.right;
        node.right = x.left;
        x.left = node;
        x.linkColor = node.linkColor;
        node.linkColor = Color.RED;
        return x;
    }
    //функция, которая поварачивает красное соединение слева-направо
    private SimpleEntry rotateRight(SimpleEntry node) {
        SimpleEntry x = node.left;
        node.left = x.right;
        x.right = node;
        x.linkColor = node.linkColor;
        node.linkColor = Color.RED;
        return x;
    }
    //меняет цвета соединений для преобразования структуры к-ч-бинарного дерева,
    //что бы обеспечить логарифмическую длину веток 
    private void flipColors(SimpleEntry node) {
        node.linkColor = Color.RED;
        node.left.linkColor = Color.BLACK;
        node.right.linkColor = Color.BLACK;
    }

    @Override
    public void put(Object key, Object value) {
        currentDeletion = null;
        root = put(root, key, value);
    }

    private SimpleEntry put(SimpleEntry node, Object key, Object value) {
        if (node == null) {
            size++;
            return new SimpleEntry(key, value, Color.RED);
        }
        int cmp = node.compareTo(key);
        if (cmp < 0) {
            node.right = put(node.right, key, value);
        }
        if (cmp > 0) {
            node.left = put(node.left, key, value);
        }
        if (cmp == 0) {
            node.value = value;
        }
        if (node.left != null && node.right != null) {
            if (!node.left.isRed() && node.right.isRed()) {
                node = rotateLeft(node);
            }
        }
        if (node.left != null && node.left.left != null) {
            if (node.left.isRed() && node.left.left.isRed()) {
                node = rotateRight(node);
            }
        }
        if (node.left != null && node.right != null) {
            if (node.left.isRed() && node.right.isRed()) {
                flipColors(node);
            }
        }
        return node;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Object remove(Object key) {
        root = remove(root, key);
        return currentDeletion;
    }
    //реализует алгоритм Хиббарда удаления вершины бинарного дерева
    private SimpleEntry remove(SimpleEntry node, Object key) {
        if (node == null) {
            return null;
        }
        int cmp = node.compareTo(key);
        if (cmp < 0) {
            node.right = remove(node.right, key);
        }
        if (cmp > 0) {
            node.left = remove(node.left, key);
        }
        if (cmp == 0) {
            currentDeletion = node;
            if (node.right == null) {
                size--;                
                return node.left;
            }
            SimpleEntry x = minimal(node.right);
            x.right = deleteMin(node.right);
            x.left = node.left;
            return x;

        }
        return node;
    }
    //нахождения минимального ключа
    private SimpleEntry minimal(SimpleEntry node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
    // удаление элемента с минимальным ключом
    private SimpleEntry deleteMin(SimpleEntry node) {
        if (node.left == null) {
            size--;
            return node.right;
        }
        node.left = deleteMin(node.left);
        return node;
    }

    @Override
    public boolean isEmpty() {
        if (this.size == 0) {
            return true;
        }
        return false;

    }

    @Override
    public Object get(Object key) {
        return get(root, key);
    }

    private SimpleEntry get(SimpleEntry node, Object key) {
        if (node == null) {
            return null;
        }
        int cmp = node.compareTo(key);
        if (cmp < 0) {
            return get(node.right, key);
        }
        if (cmp > 0) {
            return get(node.left, key);
        }
        return node;
    }

    @Override
    public boolean containsValue(Object value) {
        containsValue(root, value);
        return flag;
    }

    private void containsValue(SimpleEntry node, Object value) {
        if (node.getValue().equals(value)) {
            flag = true;
        }
        if (node.left != null && flag == false) {
            containsValue(node.left, value);
        }
        if (node.right != null && flag == false) {
            containsValue(node.right, value);
        }
    }

    @Override
    public boolean containsKey(Object key) {
        if (get(key) == null) {
            return false;
        }
        return true;
    }

    @Override
    public void clear() {
        root.left = null;
        root.right = null;
        root = null;
        currentDeletion = null;
        size = 0;
    }

    LinkedList toList() {
        LinkedList list = new LinkedList();
        toList(root, list);
        return list;
    }
    //отсортированный лист для нашого бинарного дерева
    private void toList(SimpleEntry node, LinkedList list) {
        if (node == null) {
            return;
        }
        if (node.left == null) {
            list.addLast(node);
            toList(node.right, list);
        } else {
            toList(node.left, list);
            list.add(node);
            toList(node.right, list);
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        LinkedList list = toList();
        s.append("list of sorted pairs:\n");
        for (int i = 0; i < size; i++) {
               s.append(list.get(i).toString());
        }
        return s.toString();
    }

}
