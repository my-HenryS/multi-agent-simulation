package org.socialforce.drawer.impl;

import org.socialforce.geom.*;
import org.socialforce.model.Agent;
import org.socialforce.model.impl.Star_Planet;

import java.awt.*;

/**
 * Created by Ledenel on 2017/3/3.
 */
public class PositionDynamicColorMarkDrawer extends DynamicColorDrawer<Star_Planet> {
    public PositionDynamicColorMarkDrawer(Graphics2D device) {
        super(device);
    }

    protected int quant(double v, double x, double xs) {
        return (int) (Math.abs(x - xs) / v * 255);
    }

    @Override
    public Color currentColor(Star_Planet agent) {
        Box size = agent.getScene().getBounds();
        double [] sz = new double[2];
        size.getSize().get(sz);
        org.socialforce.geom.Point p = agent.getShape().getReferencePoint();
        int xq = quant(sz[0],p.getX(),size.getStartPoint().getX());
        int yq = quant(sz[1],p.getY(),size.getStartPoint().getY());
        return new Color(255-xq,yq,0);
    }
}
