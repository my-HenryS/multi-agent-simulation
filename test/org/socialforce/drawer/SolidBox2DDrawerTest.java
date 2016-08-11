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
public class SolidBox2DDrawerTest {
    SolidBox2DDrawer drawer;
    BufferedImage image;
    Box2D box;


    @Before
    public void setUp() throws Exception {
        image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
        box = new Box2D(0, 0, 3, 4);
        Graphics2D gra = image.createGraphics();


        // transform shape to a coorniate x[-5,5], y[-5,5].

        AffineTransform transform = new AffineTransform();

        transform.scale(image.getWidth() / 10.0, image.getHeight() / 10.0);
        transform.translate(0, 10.0);
        transform.scale(1, -1);
        transform.translate(5.0, 5.0);

        gra.transform(transform);


        drawer = new SolidBox2DDrawer(gra, null);
        drawer.setColor(0xFFFF0000);


    }

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


}