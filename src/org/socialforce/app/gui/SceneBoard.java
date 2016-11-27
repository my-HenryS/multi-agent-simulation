package org.socialforce.app.gui;

import org.socialforce.app.Scene;
import org.socialforce.drawer.impl.SceneDrawer;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Ledenel on 2016/10/23.
 */
public class SceneBoard extends JPanel {
    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    Scene scene;

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    BufferedImage image;

    public void refresh() {
        if(image != null) {
            this.getGraphics().drawImage(image,0,0,null);
        }
    }

    /**
     * Calls the UI delegate's paint method, if the UI delegate
     * is non-<code>null</code>.  We pass the delegate a copy of the
     * <code>Graphics</code> object to protect the rest of the
     * paint code from irrevocable changes
     * (for example, <code>Graphics.translate</code>).
     * <p>
     * If you override this in a subclass you should not make permanent
     * changes to the passed in <code>Graphics</code>. For example, you
     * should not alter the clip <code>Rectangle</code> or modify the
     * transform. If you need to do these operations you may find it
     * easier to create a new <code>Graphics</code> from the passed in
     * <code>Graphics</code> and manipulate it. Further, if you do not
     * invoker super's implementation you must honor the opaque property,
     * that is
     * if this component is opaque, you must completely fill in the background
     * in a non-opaque color. If you do not honor the opaque property you
     * will likely see visual artifacts.
     * <p>
     * The passed in <code>Graphics</code> object might
     * have a transform other than the identify transform
     * installed on it.  In this case, you might get
     * unexpected results if you cumulatively apply
     * another transform.
     *
     * @param g the <code>Graphics</code> object to protect
     * @see #paint
     * @see ComponentUI
     */

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(scene != null) {
            SceneDrawer sc = (SceneDrawer) scene.getDrawer();
            synchronized (sc) {
                sc.setCtrlHeight(this.getHeight());
                sc.setCtrlWidth(this.getWidth());
            }
        }
        refresh();
    }
}
