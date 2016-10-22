package org.socialforce.app;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Whatever on 2016/10/22.
 */
public class SimpleApplicationTest {
    SimpleApplication application = new SimpleApplication();
    @Test
    public void start() throws Exception {
        application.start();
    }

}