package org.socialforce.drawer.impl;

import org.socialforce.geom.*;
import org.socialforce.model.impl.BaseAgent;

import java.awt.*;

/**
 * Created by Ledenel on 2017/3/3.
 */
public class PositionAgentMarkDrawer extends TargetAgentMarkDrawer {
    public PositionAgentMarkDrawer(Graphics2D device) {
        super(device);
    }

    @Override
    public Color currentColor(BaseAgent agent) {
        Box size = agent.getScene().getBounds();
        double [] sz = new double[2];
        size.getSize().get(sz);
        org.socialforce.geom.Point p = agent.getShape().getReferencePoint();
        int xq = quant(sz[0],p.getX(),size.getStartPoint().getX());
        int yq = quant(sz[1],p.getY(),size.getStartPoint().getY());
        return new Color(255-xq,yq,0);
    }
}
