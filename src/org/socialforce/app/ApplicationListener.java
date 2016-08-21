package org.socialforce.app;

import org.socialforce.model.Agent;

/**
 * the listener for a social force simulation application.
 * @author Ledenel
 * @see SocialForceApplication
 * Created by Ledenel on 2016/8/3.
 */
public interface ApplicationListener {
    /**
     * triggered while the application is start.
     */
    void onStart();

    /**
     * triggered while the application is stop.
     * @param terminated whether the application is forced to shut down.
     */
    void onStop(boolean terminated);

    /**
     * triggered while a scene is step-forwarded.
     * @param scene the scene steped-forwarded.
     */
    void onStep(Scene scene);

    /**
     * triggered while a agent is escaped.
     * @param scene the scene where the agent in.
     * @param escapeAgent the escaped agent.
     */
    void onAgentEscape(Scene scene, Agent escapeAgent);
}
