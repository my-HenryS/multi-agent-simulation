package org.socialforce.strategy.impl;

import org.socialforce.geom.Point;
import org.socialforce.geom.impl.Point2D;

/**
 * Created by sunjh1999 on 2016/12/24.
 */
public class Width {
    //暂时将门宽硬编码到goal中
    //TODO 在application层实现exit-safetyregion-goal三项结合的方法
    public static double widthOf(Point goal){
        if (goal.equals(new Point2D(-2.5,1.5))) return 1.36;
        if (goal.equals(new Point2D(20.5,-2.5))) return 1.36;
        if (goal.equals(new Point2D(30.5,19.5))) return 1.36;
        return 0;
    }
}
