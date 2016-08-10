package org.socialforce.test.util;

import junit.framework.AssertionFailedError;
import org.junit.Assert;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Ledenel on 2016/8/10.
 */
public class ImageUtils {
    public static void assertImageSimilar(BufferedImage expected, BufferedImage actual) {
        if (expected.getWidth() != actual.getWidth() && expected.getHeight() != actual.getHeight()) {
            Assert.fail(String.format("the size of images are not same. \n Expected:{%d,%d} \n Actual:{%d,%d}",
                    expected.getWidth(),
                    expected.getHeight(),
                    actual.getWidth(),
                    actual.getHeight()));
        }
        int[] expectedColors = getRgb(expected);
        int[] actualColors = getRgb(actual);
        for (int i = 0; i < expectedColors.length; i++) {
            int sd = squareDelta(expectedColors[i],actualColors[i]);
            if (sd > 25) {
                Assert.fail(String.format("images are not same at the pixel(%d,%d). \n Expected:{%X} \n Actual{%X}",
                        i / expected.getHeight(),
                        i % expected.getHeight(),
                        expectedColors[i],
                        actualColors[i]));
            }
        }
    }

    private static int squareDelta(int exp,int act) {
        int res = 0;
        while(exp > 0 || act > 0) {
            int d = (exp & 0xFF) - (act & 0xFF);
            res += d*d;
            exp >>= 8;
            act >>= 8;
        }
        return res;
    }


    private static int[] getRgb(BufferedImage expected) {
        return expected.getRGB(0, 0, expected.getWidth(), expected.getHeight(), null, 0, expected.getHeight());
    }
}
