package org.socialforce.geom.impl;

import org.socialforce.geom.*;

/**
 * Created by Whatever on 2017/8/16.
 */
abstract class Shape2D implements PhysicalEntity {
    Velocity currVelocity, currAcceleration = new Velocity2D(0,0);
    Force pushed = new Force2D(0,0);
    Double mass;
    protected static double forceUpbound = Double.MAX_VALUE;



    @Override
    public Velocity getVelocity(){
        return currVelocity;
    }

    @Override
    public void act(double time){
        if(pushed.length() > forceUpbound){
            this.pushed = pushed.getRefVector();
            pushed.scale(forceUpbound);
        }
        Velocity next_v = new Velocity2D(0,0), deltaV = this.pushed.deltaVelocity(mass, time);
        currAcceleration = deltaV.clone();
        currAcceleration.scale(1/time);//TODO 为什么还要再scale一次，查明。
        Vector deltaS;
        next_v.add(currVelocity);
        next_v.add(deltaV);
        this.currVelocity.add(deltaV);
        deltaS = next_v.deltaDistance(time);
        Point point = getReferencePoint();
        point.add(deltaS);
        this.moveTo(point);
        pushed.scale(0);
    }

    @Override
    public void push(Affection affection){
        if(affection.getForce() != null){
            pushed.add(affection.getForce());
        }
    }

    @Override
    public double getMass(){
        return mass;
    }

    @Override
    public void setMass(double mass){
        this.mass = mass;
    }

    @Override
    public void setVelocity(Velocity velocity){
        this.currVelocity = velocity;
    }

    @Override
    public Velocity getAcceleration(){
        return currAcceleration;
    }

    @Override
    public abstract PhysicalEntity clone();
}
