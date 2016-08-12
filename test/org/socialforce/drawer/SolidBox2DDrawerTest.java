package org.socialforce.drawer;

import org.junit.Before;
import org.junit.Test;
import org.socialforce.entity.impl.Box2D;
import org.socialforce.entity.impl.Point2D;
import org.socialforce.test.util.ImageUtils;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import static org.junit.Assert.*;

/**
 * Created by Ledenel on 2016/8/10.
 */
public class SolidBox2DDrawerTest extends AwtMathDrawerTest {
    SolidBox2DDrawer drawer;

    @Test
    public void renderBoxShape() throws Exception {
        drawer.setBox(new Box2D(0, 0, 3, 4));
        drawer.draw();
        ImageUtils.assertImageSimilar(
                ImageIO.read(SolidBox2DDrawerTest.class.getResourceAsStream("SolidBox2DDrawer_base.png")),
                image);

    }

    @Test
    public void renderBoxOverflow() throws Exception {
        drawer.setBox(new Box2D(-10, -10, 8, 9));
        drawer.draw();
        ImageUtils.assertImageSimilar(
                ImageIO.read(SolidBox2DDrawerTest.class.getResourceAsStream("SolidBox2DDrawer_base_overflow.png")),
                image);
    }


    @Override
    protected void drawerInit(Graphics2D gra) {
        drawer = new SolidBox2DDrawer(gra, null);
        drawer.setColor(0xFFFF0000);
    }
}