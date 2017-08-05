package org.socialforce.app;

/**
 * the listener for a social force simulation application.
 * @author Ledenel
 * @see Application
 * Created by Ledenel on 2016/8/3.
 */
public interface ApplicationListener extends AgentEscapeListener {
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
     * triggered while a application is step-forwarded.
     * @param application the application steped-forwarded.
     */
    void onStep(Application application);

}
