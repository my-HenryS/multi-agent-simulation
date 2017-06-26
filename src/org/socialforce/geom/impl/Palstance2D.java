package org.socialforce.geom.impl;

import org.socialforce.geom.Palstance;

/**
 * Created by Administrator on 2017/6/26 0026.
 */
public class Palstance2D implements Palstance {
    double omega;
    Palstance2D(){}
    public Palstance2D(double omega){
        this.omega = omega;
    }
    @Override
    public double deltaAngle(double time) {
        return time*omega;
    }

    @Override
    public Palstance clone() {
        return new Palstance2D(omega);
    }

    @Override
    public void add(Palstance other) {
        if (other instanceof Palstance2D){
            omega += ((Palstance2D) other).getOmega();
        }
    }

    @Override
    public void scale(double rate) {
        omega = omega*rate;
    }

    public double getOmega(){
        return omega;
    }
}
