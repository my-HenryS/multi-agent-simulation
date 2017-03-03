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

    public static final double a[] = {7395,33405,-40800};
    public static final double b[] = {34170, -48960, 14790};
    public static final double c[] = {-10145,9187,24080};
    public static final double exrt = 255 / 5896110;

    public int limit(double v) {
        int res = (int)v;
        if(res > 255) {
            res = 255;
        }
        if(res < 0) {
            res = 0;
        }
        return res;
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

        double colorR = quantX * a[0] + quantY * b[0] + 255 * c[0];
        double colorG = quantX * a[1] + quantY * b[1] + 255 * c[1];
        double colorB = quantX * a[2] + quantY * b[2] + 255 * c[2];

        return new Color(limit(colorR), limit(colorG), limit(colorB));
    }
}
