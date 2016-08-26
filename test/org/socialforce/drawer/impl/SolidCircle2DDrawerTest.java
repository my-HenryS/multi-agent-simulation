package org.socialforce.drawer.impl;

import org.junit.Test;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.test.util.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;

/**
 * Created by Ledenel on 2016/8/12.
 */
public class SolidCircle2DDrawerTest extends AwtMathDrawerTest {
    @Test
    public void renderShape() throws Exception {
        Circle2D circle2D = new Circle2D();
        circle2D.setRadius(3);
        circle2D.moveTo(new Point2D(-1, -1));
        drawer.draw(circle2D);
        ImageUtils.assertImageSimilar(image,
                ImageIO.read(SolidCircle2DDrawerTest.class.getResourceAsStream("SolidCircle2DDrawer_base.png")));
    }

    SolidCircle2DDrawer drawer;

    @Override
    protected void drawerInit(Graphics2D gra) {
        drawer = new SolidCircle2DDrawer(gra, null);
        drawer.setColor(0xFFFF0000);
    }
}