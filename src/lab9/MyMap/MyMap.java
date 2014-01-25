/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab9.MyMap;

/**
 *
 * @author serg
 */
public interface MyMap {

    void clear();

    boolean containsKey(Object key);

    boolean containsValue(Object value);

    Object get(Object key);

    boolean isEmpty();

    void put(Object key, Object value);

    Object remove(Object key);

    int size();

    //Object entryIterator();

    interface Entry {

        @Override
        boolean equals(Object o);

        Object getKey();

        Object getValue();

        @Override
        int hashCode();

        void setValue(Object o);
    }
};
