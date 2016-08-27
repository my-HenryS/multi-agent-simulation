package org.socialforce.drawer.impl;

import org.socialforce.app.ProxyedGraphics2D;
import org.socialforce.app.Scene;
import org.socialforce.drawer.Drawable;
import org.socialforce.drawer.Drawer;
import org.socialforce.drawer.DrawerInstaller;
import org.socialforce.model.Agent;
import org.socialforce.model.InteractiveEntity;

import java.awt.*;

/**
 * Created by Ledenel on 2016/8/24.
 */
public class SceneDrawer implements Drawer<ProxyedGraphics2D,Scene> {
    /**
     * draw the pattern on the specific device.
     * @param pattern
     */
    @Override
    public void draw(Scene pattern) {
        for (InteractiveEntity entity : pattern.getAllEntitiesStream()::iterator) {
            Drawer drawer = entity.getShape().getDrawer();
            drawer.setDevice(this.getDevice());
            drawer.draw(entity.getShape());
        }
        // 2016/8/24  add draw scene.

    }

    protected DrawerInstaller installer;

    protected ProxyedGraphics2D graphics2D;

    public SceneDrawer(Graphics2D graphics) {
        // TODO: 2016/8/27 add coordinate transform for graphics.
        graphics2D = new ProxyedGraphics2D(graphics);
        installer = new ShapeDrawer2DInstaller(graphics);
       // scene.setDrawer(this);

//        Iterable<Agent> agents = scene.getAllAgents();
//        for(Agent agent : agents) {
//            //agent.getShape().setDrawer();
//            // 2016/8/24 add factory method for drawer.
//
//        }
        // 2016/8/24 set device for scene entities. done in installer.
    }

    protected Scene scene;

    protected int color;

    /**
     * get the primary color of the pattern. <br>
     * the color scheme is ARGB.<br>
     * CAUTION: there is no guarantee that the color is the color drawed on the device.
     * please call draw() before to sync the color you get from the device.
     *
     * @return the ARGB-format color.
     */
    @Override
    public int getColor() {
        return color;
    }

    /**
     * set the primary color of the pattern. <br>
     * the color scheme is ARGB.<br>
     * CAUTION: there is no guarantee that the pattern on the device will changed.
     * please call draw() to ensure that the changes have been applied on the device.
     *
     * @param color the ARGB-format color to set.
     */
    @Override
    public void setColor(int color) {
        this.color = color;
    }


    /**
     * get the device of the drawer.
     *
     * @return the device which the drawer is using.
     */
    @Override
    public ProxyedGraphics2D getDevice() {
        return graphics2D;
    }

    /**
     * set the device for the drawer.
     *
     * @param device the device for the drawer.
     */
    @Override
    public void setDevice(ProxyedGraphics2D device) {
        this.graphics2D = device;
    }
}
