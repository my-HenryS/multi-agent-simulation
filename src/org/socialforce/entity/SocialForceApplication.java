package org.socialforce.entity;

/**
 * Created by Ledenel on 2016/8/3.
 */
public interface SocialForceApplication {
    void start();
    SocialForceModel getModel();
    void setModel(SocialForceModel model);
    ApplicationListener getApplicationListener();
    void setApplicationListener(ApplicationListener listener);
}
