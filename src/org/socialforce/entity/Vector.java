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
     * check if this is <strong>strictly</strong> equals to another vector.
     * @param other the vector to be checked.
     * @return true if the dimensions are same and all the components are same; false otherwise.
     */
    boolean equals(Vector other);

    /**
     * check if the vector is approximately equal to other vector within the epsilon.
     * two vectors are approximately equal only if the L-infinite distance between this and vector other is less than or equal to the epsilon.
     * The L-infinite distance is equal to MAX[abs(x1-x2), abs(y1-y2)].
     * @param other the vector other.
     * @param epsilon the epsilon.
     * @return Returns true if the vector is approximately equals with other,
     * otherwise returns false.
     */
    boolean epsilonEquals(Vector other, double epsilon);
    
    /**
     * get the length of the vector.
     * |<strong>v</strong>| in math.
     * @return the length.
     */
    double length();
    
    /**
     * Computes the dot product of the this vector and other.
     * |<strong>a</strong>||<strong>b</strong>|Cos&lt;<strong>a</strong>,<strong>b</strong>&gt; in math.
     * @return the dot product.
     */
    double dot(Vector other);
    
    /**
     * Copies the value of this vector into the array arrayToCopy.
     * @param arrayToCopy array to copy.
     */
    void get(double [] arrayToCopy);
    
    /**
     * set this vector's values to array values.
     * @param values the values to be set.
     */
    void set(double [] values);

    /**
     * Creates and returns a copy of this vector.
     * The precise meaning of "copy" may depend on the class of the vector.
     * The general intent is that, for any vector x, the expression: <br>
     * x.clone() != x <br>
     * will be true.
     * @return the copied vector.
     */
    Vector clone();
}
