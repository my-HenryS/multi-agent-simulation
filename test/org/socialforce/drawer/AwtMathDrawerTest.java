package org.socialforce.drawer;

import org.junit.Before;
import org.socialforce.entity.impl.Box2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Created by Ledenel on 2016/8/12.
 */
public abstract class AwtMathDrawerTest {
    protected BufferedImage image;

    @Before
    public void setUp() throws Exception {
        image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gra = image.createGraphics();


        // transform shape to a coorniate x[-5,5], y[-5,5].

        AffineTransform transform = new AffineTransform();

        transform.scale(image.getWidth() / 10.0, image.getHeight() / 10.0);
        transform.translate(0, 10.0);
        transform.scale(1, -1);
        transform.translate(5.0, 5.0);

        gra.transform(transform);


        drawerInit(gra);


    }

    protected abstract void drawerInit(Graphics2D gra);

}
