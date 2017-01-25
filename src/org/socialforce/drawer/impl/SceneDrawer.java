package org.socialforce.drawer.impl;

import org.socialforce.app.ProxyedGraphics2D;
import org.socialforce.model.impl.BaseAgent;
import org.socialforce.model.impl.SelfDrivingCar;
import org.socialforce.scene.Scene;
import org.socialforce.drawer.Drawer;
import org.socialforce.drawer.DrawerInstaller;
import org.socialforce.geom.Box;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.model.Agent;
import org.socialforce.model.InteractiveEntity;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Rectangle2D;

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
        Iterable<InteractiveEntity> iterable = pattern.getAllEntitiesStream()::iterator;
        AffineTransform transform = getTransform();
        AffineTransform reverse = null;
        try {
            reverse = transform.createInverse();
        } catch (NoninvertibleTransformException e) {
            e.printStackTrace();
        }
        getDevice().transform(transform);
        getDevice().setColor(Color.WHITE);
        double pt[] = new double[2];
        double sz[] = new double[2];
        clip.getStartPoint().get(pt);
        clip.getSize().get(sz);
        getDevice().fill(new Rectangle2D.Double(pt[0],pt[1],sz[0],sz[1]));
        for (InteractiveEntity entity : iterable) {
            Drawer drawer = entity.getShape().getDrawer();
            if(drawer != null) {
                drawer.setDevice(this.getDevice());
                if(entity instanceof BaseAgent) ((AwtDrawer2D)drawer).setColor(Color.red);
                if(entity instanceof SelfDrivingCar) ((AwtDrawer2D)drawer).setColor(Color.blue);
                drawer.draw(entity.getShape());
                ((AwtDrawer2D)drawer).setColor(Color.black);
            }
        }
        getDevice().transform(reverse);
        // 2016/8/24  add draw scene.
    }

    public Box getClip() {
        return clip;
    }

    public void setClip(Box clip) {
        this.clip = clip;
    }

    Box clip;

    protected DrawerInstaller installer;

    protected ProxyedGraphics2D graphics2D;

    public double getCtrlWidth() {
        return ctrlWidth;
    }

    public void setCtrlWidth(double ctrlWidth) {
        this.ctrlWidth = ctrlWidth;
    }

    public double getCtrlHeight() {
        return ctrlHeight;
    }

    public void setCtrlHeight(double ctrlHeight) {
        this.ctrlHeight = ctrlHeight;
    }

    double ctrlWidth;
    double ctrlHeight;

    public SceneDrawer(Graphics2D graphics, double ctrlWidth, double ctrlHeight) {
        // TODO: 2016/8/27 add coordinate transform for graphics.

        clip = new Box2D(0, 0, 250, 250);
        this.ctrlHeight = ctrlHeight;
        this.ctrlWidth = ctrlWidth;

        //graphics.transform(getTransform());

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

    protected AffineTransform getTransform() {
        AffineTransform transform = new AffineTransform();
//-50 -50 50 50
//-10 -10 40 30
        double cp[] = new double[2];
        double start[] = new double[2];
        clip.getSize().get(cp);
        clip.getStartPoint().get(start);
        transform.scale(ctrlWidth / cp[0], ctrlHeight / cp[1]);
        transform.translate(0, cp[1]);
        transform.scale(1, -1);
        transform.translate(-start[0],-start[1]);

        //transform.re
        return transform;
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
