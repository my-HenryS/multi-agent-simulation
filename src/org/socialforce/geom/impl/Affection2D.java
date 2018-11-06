package org.socialforce.geom.impl;

import org.socialforce.geom.Affection;
import org.socialforce.geom.Force;
import org.socialforce.geom.Moment;

/**
 * 所有Affection的简单组合
 * 目前包含Force和Moment
 * Created by sunjh1999 on 2017/10/16.
 */
public class Affection2D implements Affection {
    Moment2D moment2D = new Moment2D(0);
    Force2D force2D = new Force2D(0,0);


    public Affection2D(){}
    public Affection2D(Moment2D moment2D, Force2D force2D){
        this.moment2D = moment2D;
        this.force2D = force2D;
    }

    @Override
    public Force getForce() {
        return force2D;
    }

    @Override
    public Moment getMoment() {
        return moment2D;
    }

    @Override
    public void add(Affection affection) {
        force2D.add(affection);
        moment2D.add(affection);
    }
}
