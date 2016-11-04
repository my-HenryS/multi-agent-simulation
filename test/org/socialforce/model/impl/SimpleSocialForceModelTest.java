package org.socialforce.model.impl;

import org.junit.Test;
import org.socialforce.model.SocialForceModel;

import static org.junit.Assert.*;

/**
 * Created by sunjh1999 on 2016/11/4.
 */
public class SimpleSocialForceModelTest {
    @Test
    public void getInstance() throws Exception {
        SocialForceModel model = SocialForceModel.getInstance();
        SocialForceModel model2 = SocialForceModel.getInstance();
        SocialForceModel model3 = new SimpleSocialForceModel();
        assertTrue(model.equals(model2));
        assertTrue(model==model2);
        assertFalse(model==model3);
    }

}