package org.socialforce.model;

import org.socialforce.geom.Moment;
import org.socialforce.geom.Palstance;

/**
 * Created by Administrator on 2017/6/26 0026.
 */
public interface Rotateable extends InteractiveEntity {
    Palstance getPalstance();
    double getIntetia();
}
