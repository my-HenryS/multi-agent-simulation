package org.socialforce.geom.impl;

import org.socialforce.geom.*;

/**
 * Created by Whatever on 2017/8/16.
 */
public abstract class RotatableShape2D extends Shape2D implements RotatablePhysicalEntity {
    double inertia;
    Moment spined = new Moment2D(0);
    Palstance currPal=new Palstance2D(0),currAccPal = new Palstance2D(0);
    private static double momentUpbound = Double.MAX_VALUE;

    @Override
    public Palstance getPalstance(){
        return currPal;
    }

    @Override
    public double getInertia(){
        return inertia;
    }

    @Override
    public void push(Affection affection){
        super.push(affection);
        if (affection instanceof Moment2D){
            spined.add((Moment) affection);
        }
    }

    @Override
    public void act(double time){
        super.act(time);
        if (Math.abs(((Moment2D)spined).getM())> momentUpbound){
            spined.scale(momentUpbound/Math.abs(((Moment2D) spined).getM()));
        }
        Palstance next_Omega = new Palstance2D(0),deltaP = this.spined.deltaPalstance(inertia,time);
        double deltaAngle;
        next_Omega.add(currPal);
        next_Omega.add(deltaP);
        deltaAngle = next_Omega.deltaAngle(time);
        this.currPal.add(deltaP);
        this.spin(deltaAngle);
        spined.scale(0);
    }

    @Override
    public void setPalstance(Palstance omega){
        currPal = omega;
    }

    @Override
    public void setInertia(double inertia){
        this.inertia = inertia;
    }

    @Override
    public abstract PhysicalEntity clone();
}
