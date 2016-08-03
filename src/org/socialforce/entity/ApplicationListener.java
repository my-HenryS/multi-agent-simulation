package org.socialforce.entity;

/**
 * Created by Ledenel on 2016/8/3.
 */
public interface ApplicationListener {
    void onStart();
    void onStop();
    void onStep(Scene scene);
    void onAgentEscape(Scene scene, Agent escapeAgent);
}
