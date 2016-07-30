package org.socialforce.entity;

import java.io.Serializable;

/**
 * Represent a vector like <strong>a</strong> = (a<sub>1</sub>,a<sub>2</sub>,a<sub>3</sub>,...).
 *
 * vector can be add, substract with other vectors and scale to a new size. <br>
 * (use <code>double</code> to represent vector components.)
 * @author Ledenel
 *
 * Created by Ledenel on 2016/7/25.
 */
public interface Vector extends Cloneable, Serializable, DimensionEntity{
    /**
     * get the dimension of the vector.
     * @return the dimension.
     */
    int dimension();

    /**
     * add another vector on this vector.
     * act like this = this + other.
     * @param other the other vector to be added.
     */
    void add(Vector other);

    /**
     * subtract another vector on this vector.
     * act like this = this - other.
     * @param other the other vector to be subtracted.
     */
    void sub(Vector other);

    /**
     * multiply a number on this vector.
     * act like this = this * rate.
     * @param rate the rate to be multiplied.
     */
    void scale(double rate);

    /**
     * check if the object is a <code>Vector</code> and <strong>strictly</strong> equals to this.
     * @param object the object to be checked.
     * @return true if the object equals to this; false otherwise.
     */
    boolean equals(Object object);

    /**
     * check if this is <strong>strictly</strong> to another vector.
     * @param other the vector to be checked.
     * @return true if the dimensions are same and all the components are same; false otherwise.
     */
    boolean equals(Vector other);

    /**
     * @todo not sure what is it
     */
    boolean epsilonEquals(Vector other, double epsilon);
    
    /**
     * get the length of the vector
     */
    double length();
    
    /**
     * @todo not sure what is it
     */
    double dot(Vector other);
    
    /**
     * set vector from a array
     */
    void get(double [] arrayToCopy);
    
    /**
     * set vector from a scalar
     */
    void set(double [] values);
}
