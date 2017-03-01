package org.socialforce.drawer.impl;

import org.socialforce.geom.*;
import org.socialforce.model.impl.BaseAgent;

import java.awt.*;

/**
 * Created by Ledenel on 2017/3/1.
 */
public class TargetAgentMarkDrawer extends AgentDrawer {
    public TargetAgentMarkDrawer(Graphics2D device) {
        super(device);
    }

    /**
     * 依据Agent的当前状态，决定agent所应绘制的颜色。
     * 这个实现默认为黑色（可以改为任何颜色。）
     *
     * @param agent 要决定的agent的颜色。
     * @return
     */
    @Override
    public Color currentColor(BaseAgent agent) {
        Box size = agent.getScene().getBounds();
        org.socialforce.geom.Point goal = agent.getPath().getGoal();
        double [] sz = new double[2];
        size.getSize().get(sz);
        int quantX = (int) (Math.abs(goal.getX() - size.getStartPoint().getX()) / sz[0] * 255);
        int quantY = (int) (Math.abs(goal.getY() - size.getStartPoint().getY()) / sz[1] * 255);
        return new Color(quantX, quantX, 100);
    }
}
