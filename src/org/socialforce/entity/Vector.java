package org.socialforce.entity;

import java.io.Serializable;

/**
 * Represent a vector like (a1,a2,a3,...).
 *
 * vector can be add, substract with other vectors and scale to a new size. <br>
 * (use <code>double</code> to represent vector components.)
 * @author Ledenel
 *
 * Created by Ledenel on 2016/7/25.
 */
public interface Vector extends Cloneable, Serializable{
    int dimension();
    void add(Vector other);
    void sub(Vector other);
    void scale(double rate);
    boolean equals(Object object);
    boolean equals(Vector other);
    boolean epsilonEquals(Vector other, double epsilon);
    double length();
    double dot(Vector other);
    void get(double [] arrayToCopy);
    void set(double [] values);
}
