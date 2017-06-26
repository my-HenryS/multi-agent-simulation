package org.socialforce.geom;

/**
 * 力矩
 * Created by Administrator on 2017/6/23 0023.
 */
public interface Moment extends Affection {
    Palstance deltaPalstance(double inertia,double time);
    Moment clone();
    void add(Moment other);
    void scale(double rate);
}
