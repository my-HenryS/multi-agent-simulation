package org.socialforce.drawer.impl;

import org.socialforce.app.ProxyedGraphics2D;
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
        double [] start = new double[2];
        clip = pattern.getBounds();
        clip.getReferencePoint().get(start);
        //scaleRate = calcScaleRate(pattern.getBounds());
        AffineTransform transform = getTransform(start);
        /*AffineTransform reverse = null;
        try {
            reverse = transform.createInverse();
        } catch (NoninvertibleTransformException e) {
            e.printStackTrace();
        }
/*/
        getDevice().setColor(new Color(70,70,70));
        double pt[] = new double[2];
        double sz[] = new double[2];
        clip.getStartPoint().get(pt);
        clip.getSize().get(sz);


        getDevice().fill(new Rectangle2D.Double(0,0,ctrlWidth,ctrlHeight));
        AffineTransform oldT = getDevice().getTransform();
        getDevice().transform(transform);
        getDevice().setStroke(new BasicStroke((float) (1 / getScaleRate())));
        getDevice().setColor(new Color(150,255,150));
        getDevice().draw(new Rectangle2D.Double(pt[0],pt[1],sz[0],sz[1]));
        getDevice().setColor(Color.WHITE);
        for (InteractiveEntity entity : iterable) {
            Drawer drawer = entity.getDrawer();
            if(drawer == null){
                eInstaller.addDrawerSupport(entity);
                drawer = entity.getDrawer();
            }
            //drawer.setDevice(this.getDevice());
 //         if(entity instanceof Agent && ((Agent) entity).getPath().getGoal().equals(new Point2D(26.0,2.5))) ((AwtDrawer2D)drawer).setColor(Color.red);
 //         if(entity instanceof Agent && ((Agent) entity).getPath().getGoal().equals(new Point2D(33.5,14))) ((AwtDrawer2D)drawer).setColor(Color.green);
 //         if(entity instanceof Agent && ((Agent) entity).getPath().getGoal().equals(new Point2D(14.0,21.5))) ((AwtDrawer2D)drawer).setColor(Color.blue);
            drawer.draw(entity);
            //((AwtDrawer2D)drawer).setColor(Color.black);

        }
        //getDevice().transform(reverse);
        getDevice().setTransform(oldT);
        // 2016/8/24  add draw scene.
    }

    public Box getClip() {
        return clip;
    }

    public void setClip(Box clip) {
        this.clip = clip;
    }

    Box clip;

    protected EntityDrawerInstaller eInstaller;

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

    public double getScaleRate() {
        return scaleRate;
    }

    public void setScaleRate(double scaleRate) {
        if(scaleRate > minScaleRate) {
            this.scaleRate = scaleRate;
        }
    }

    double scaleRate = 4;
    double minScaleRate = 2;

    public double getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(double offsetX) {
        this.offsetX = offsetX;
    }

    public double getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(double offsetY) {
        this.offsetY = offsetY;
    }

    double offsetX;
    double offsetY;

    public SceneDrawer(Graphics2D graphics, double ctrlWidth, double ctrlHeight) {
        // TODO: 2016/8/27 add coordinate transform for graphics.

        clip = new Box2D(-10, -10, 50, 50);
        this.ctrlHeight = ctrlHeight;
        this.ctrlWidth = ctrlWidth;

        //graphics.transform(getTransform());

        graphics2D = new ProxyedGraphics2D(graphics);
        installer = new ShapeDrawer2DInstaller(graphics);
        eInstaller = new EntityDrawerInstaller(graphics);
       // scene.setDrawer(this);

//        Iterable<Agent> agents = scene.getAllAgents();
//        for(Agent agent : agents) {
//            //agent.getShape().setDrawer();
//            // 2016/8/24 add factory method for drawer.
//
//        }
        // 2016/8/24 set device for scene entities. done in installer.
    }

    public double calcScaleRate(Box bound) {
        double sz[] = new double[2];
        bound.getSize().get(sz);
        return Math.max(minScaleRate, Math.min(ctrlWidth / sz[0],ctrlHeight / sz[1]));
    }

    protected AffineTransform getTransform(double[] center) {
        AffineTransform transform = new AffineTransform();
//-50 -50 50 50
//-10 -10 40 30
        double cp[] = new double[2];
        //transform.scale(ctrlWidth / cp[0], ctrlHeight / cp[1]);
        transform.translate(offsetX,offsetY);
        transform.translate(ctrlWidth / 2,ctrlHeight / 2);
        transform.scale(scaleRate,scaleRate);

        transform.scale(1, -1);
        transform.translate(-center[0],-center[1]);


        //transform.translate(-size[0] / 2,-size[1] / 2);


        return transform;
    }

    public double[] sceneToScreen(double ... src) {
        double [] ct = new double[2];
        clip.getReferencePoint().get(ct);
        AffineTransform transform = getTransform(ct);
        transform.transform(src,0,ct,0,1);
        return ct;
    }

    public double[] screenToScene(double ... src) {
        double [] ct = new double[2];
        clip.getReferencePoint().get(ct);
        AffineTransform transform = null;
        try {
            transform = getTransform(ct).createInverse();
        } catch (NoninvertibleTransformException e) {
            e.printStackTrace();
        }
        transform.transform(src,0,ct,0,1);
        return ct;
    }

    //protected Scene scene;

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

    public EntityDrawerInstaller getEntityDrawerInstaller(){
        return eInstaller;
    }
}
