package org.socialforce.settings;

import org.junit.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Ledenel on 2017/9/30.
 */
public class SettingScannerTest {
    @org.junit.Test
    public void inject() throws Exception {
        ArrayList<Class> classes = new ArrayList<>();
        SettingScanner.inject(clazz -> classes.add(clazz));
        assertTrue(classes.size() >= 1);
        /*for (Class clazz:classes             ) {
            System.out.println("clazz = " + clazz.getName());
        }*/
    }

}