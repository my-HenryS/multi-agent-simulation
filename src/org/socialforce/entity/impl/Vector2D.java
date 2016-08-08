package org.socialforce.entity.impl;

import org.socialforce.entity.Vector;

import java.util.HashMap;

/**
 * an simple 2-D Vector.
 *
 * @author Ledenel
 * @see Vector
 * Created by Ledenel on 2016/8/7.
 */
public class Vector2D implements Vector {
    protected double values[];

    public Vector2D() {
        values = new double[2];
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * <p>
     * The {@code equals} method implements an equivalence relation
     * on non-null object references:
     * <ul>
     * <li>It is <i>reflexive</i>: for any non-null reference value
     * {@code x}, {@code x.equals(x)} should return
     * {@code true}.
     * <li>It is <i>symmetric</i>: for any non-null reference values
     * {@code x} and {@code y}, {@code x.equals(y)}
     * should return {@code true} if and only if
     * {@code y.equals(x)} returns {@code true}.
     * <li>It is <i>transitive</i>: for any non-null reference values
     * {@code x}, {@code y}, and {@code z}, if
     * {@code x.equals(y)} returns {@code true} and
     * {@code y.equals(z)} returns {@code true}, then
     * {@code x.equals(z)} should return {@code true}.
     * <li>It is <i>consistent</i>: for any non-null reference values
     * {@code x} and {@code y}, multiple invocations of
     * {@code x.equals(y)} consistently return {@code true}
     * or consistently return {@code false}, provided no
     * information used in {@code equals} comparisons on the
     * objects is modified.
     * <li>For any non-null reference value {@code x},
     * {@code x.equals(null)} should return {@code false}.
     * </ul>
     * <p>
     * The {@code equals} method for class {@code Object} implements
     * the most discriminating possible equivalence relation on objects;
     * that is, for any non-null reference values {@code x} and
     * {@code y}, this method returns {@code true} if and only
     * if {@code x} and {@code y} refer to the same object
     * ({@code x == y} has the value {@code true}).
     * <p>
     * Note that it is generally necessary to override the {@code hashCode}
     * method whenever this method is overridden, so as to maintain the
     * general contract for the {@code hashCode} method, which states
     * that equal objects must have equal hash codes.
     *
     * @param obj the reference object with which to compare.
     * @return {@code true} if this object is the same as the obj
     * argument; {@code false} otherwise.
     * @see #hashCode()
     * @see HashMap
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Vector) {
            return this.equals((Vector)obj);
        } else {
            return false;
        }
    }

    public Vector2D(double x,double y) {
        this();
        values[0] = x;
        values[1] = y;
    }



    /**
     * get the dimension of the vector.
     *
     * @return the dimension.
     */
    @Override
    public int dimension() {
        return values.length;
    }

    /**
     * add another vector on this vector.
     * act like this = this + other.
     *
     * @param other the other vector to be added.
     */
    @Override
    public void add(Vector other) {
        double[] otherv = quiteConvert(other).values;
        for (int i = 0; i < values.length; i++) {
            values[i] += otherv[i];
        }
    }

    protected Vector2D quiteConvert(Vector vector) {
        if (vector instanceof Vector2D) {
            return (Vector2D) vector;
        } else {
            throw new IllegalArgumentException("Vector2D could only calculated with Vector2D object.");
        }
    }

    /**
     * subtract another vector on this vector.
     * act like this = this - other.
     *
     * @param other the other vector to be subtracted.
     */
    @Override
    public void sub(Vector other) {
        double[] otherv = quiteConvert(other).values;
        for (int i = 0; i < values.length; i++) {
            values[i] -= otherv[i];
        }
    }

    /**
     * multiply a number on this vector.
     * act like this = this * rate.
     *
     * @param rate the rate to be multiplied.
     */
    @Override
    public void scale(double rate) {
        for (int i = 0; i < values.length; i++) {
            values[i] *= rate;
        }
    }

    /**
     * check if this is <strong>strictly</strong> equals to another vector.
     *
     * @param other the vector to be checked.
     * @return true if the dimensions are same and all the components are same; false otherwise.
     */
    @Override
    public boolean equals(Vector other) {
        /*if (this.dimension() == other.dimension()) {
            double[] c = new double[this.dimension()];
            other.get(c);
            for (int i = 0; i < values.length; i++) {
                if (values[i] != c[i]) {
                    return false;
                }
            }
            return true;
        }
        return false;*/
        return epsilonEquals(other, 1e10-15);
    }

    /**
     * check if the vector is approximately equal to other vector within the epsilon.
     * two vectors are approximately equal only if the L-infinite distance between this and vector other is less than or equal to the epsilon.
     * The L-infinite distance is equal to MAX[abs(x1-x2), abs(y1-y2)].
     *
     * @param other   the vector other.
     * @param epsilon the epsilon.
     * @return Returns true if the vector is approximately equals with other,
     * otherwise returns false.
     */
    @Override
    public boolean epsilonEquals(Vector other, double epsilon) {
        if(this.dimension() == other.dimension()) {
            double [] c = new double[dimension()];
            other.get(c);
            for (int i = 0; i < values.length; i++) {
                if(Math.abs(values[i]-c[i]) > epsilon) {
                    return  false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * get the length of the vector.
     * |<strong>v</strong>| in math.
     *
     * @return the length.
     */
    @Override
    public double length() {
        return 0;
    }

    /**
     * Computes the dot product of the this vector and other.
     * |<strong>a</strong>||<strong>b</strong>|Cos&lt;<strong>a</strong>,<strong>b</strong>&gt; in math.
     *
     * @param other
     * @return the dot product.
     */
    @Override
    public double dot(Vector other) {
        double [] otherv = quiteConvert(other).values;
        double sum = 0;
        for (int i = 0; i < values.length; i++) {
            sum += values[i] * otherv[i];
        }
        return sum;
    }

    /**
     * Copies the value of this vector into the array arrayToCopy.
     *
     * @param arrayToCopy array to copy.
     */
    @Override
    public void get(double[] arrayToCopy) {
        System.arraycopy(values,0,arrayToCopy,0,Math.min(arrayToCopy.length,this.values.length));
    }

    /**
     * set this vector's values to array values.
     *
     * @param values the values to be set.
     */
    @Override
    public void set(double[] values) {
        System.arraycopy(values,0,this.values,0,Math.min(values.length,this.values.length));
    }

    /**
     * Creates and returns a copy of this vector.
     * The precise meaning of "copy" may depend on the class of the vector.
     * The general intent is that, for any vector x, the expression: <br>
     * x.clone() != x <br>
     * will be true.
     *
     * @return the copied vector.
     */
    @Override
    public Vector clone() {
        Vector2D vec = new Vector2D();
        System.arraycopy(values,0,vec.values,0,values.length);
        return vec;
    }
}
