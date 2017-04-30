package org.socialforce.geom.impl;

/**
 * Created by sunjh1999 on 2017/4/30.
 */
public class Tuple2D<K,V> {
    private K first;
    private V second;

    public Tuple2D(K first, V second){
        this.first = first;
        this.second = second;
    }

    public K getFirst(){ return first; }

    public V getSecond(){ return second; }

}
