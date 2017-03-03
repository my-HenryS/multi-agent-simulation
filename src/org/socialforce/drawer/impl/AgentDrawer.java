package org.socialforce.drawer.impl;

import org.socialforce.geom.*;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.impl.BaseAgent;

import java.awt.*;
import java.awt.geom.Line2D;

/**
 * 一个绘制Agent的绘制器。可以通过Agent的状态自主改变颜色。
 * 可以继承并扩展该绘制器实现更多功能（依据具体策略改变颜色，画出方向等）。
 */
public class AgentDrawer extends EntityDrawer<BaseAgent> {
    public AgentDrawer(Graphics2D device) {
        super(device);
    }

    /**
     * 依据Agent的当前状态，决定agent所应绘制的颜色。
     * 这个实现默认为黑色（可以改为任何颜色。）
     * @param agent 要决定的agent的颜色。
     * @return
     */
    public Color currentColor(BaseAgent agent) {
        return Color.BLACK;
    }

    /**
     * render the shape on the @code {Graphics2D} with color built-in.
     *
     * @param g       the graphics
     * @param pattern
     */
    @Override
    public void renderShape(Graphics2D g, BaseAgent pattern) {
        color = currentColor(pattern);
        super.renderShape(g, pattern);
//        this.setColor(new Color(255-color.getRed(),255-color.getGreen(),255-color.getBlue()));
//        org.socialforce.geom.Point pt = pattern.getShape().getReferencePoint();
//        Vector target = pt.clone();
//        Vector scP = pattern.expect().clone();
//        scP.scale(0.07);
//        target.add(scP);
//        double [] targetp = new double[2];
//        target.get(targetp);
//        g.draw(new Line2D.Double(pt.getX(), pt.getY(), targetp[0], targetp[1]));

    }
}
