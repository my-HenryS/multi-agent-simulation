package org.socialforce.drawer.impl;

import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.impl.BaseAgent;

import java.awt.*;

/**
 * Created by Ledenel on 2017/3/1.
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

    }
}
