package org.socialforce.entity;

/**
 * Created by Ledenel on 2016/7/30.
 */
public interface SocialForceModel {
    double getTimePerStep();
    Force calcualte(InteractiveEntity source, InteractiveEntity target);
}
