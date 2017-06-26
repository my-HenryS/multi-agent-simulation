package org.socialforce.geom.impl;

import org.socialforce.geom.Moment;
import org.socialforce.geom.Palstance;

/**力矩
 * Created by Administrator on 2017/6/23 0023.
 */
public class Moment2D implements Moment {
    double M;
    public Moment2D(){}
    public Moment2D(double moment){M = moment;}


    @Override
    public Palstance deltaPalstance(double inertia, double time) {
        return new Palstance2D(M*time/inertia);
    }

    @Override
    public Moment clone() {
        return new Moment2D(M);
    }

    @Override
    public void add(Moment other) {
        if (other instanceof Moment2D)
        {
            M+=((Moment2D) other).getM();
        }
    }

    @Override
    public void scale(double rate) {
        M = M*rate;
    }

    public double getM(){
        return M;
    }
}
