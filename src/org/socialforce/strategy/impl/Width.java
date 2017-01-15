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
        if (goal.equals(new Point2D(26.0,2.5))) return 1.75;
        if (goal.equals(new Point2D(33.5,14))) return 1;
        if (goal.equals(new Point2D(14.0,21.5))) return 1.5;
        if (goal.equals(new Point2D(2.5,10.0))) return 0.75;
        return 0;
    }
}
