package org.socialforce.app.Applications;

import javafx.application.Application;
import org.socialforce.app.ApplicationListener;
import org.socialforce.app.SocialForceApplication;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by Ledenel on 2017/3/2.
 */
public class ApplicationLoader extends LinkedList<SocialForceApplication> implements Collection<SocialForceApplication> {
    private ApplicationListener listener;

    /**
     * 使用一个指定的ApplicationListener构造一个loader。
     * 使用如下的预设场景办法。
     */
    public ApplicationLoader(ApplicationListener listener) {
        super();
        this.listener = listener;
        add(new ApplicationForCanteen());
        add(new ApplicationForDoorTest());
        add(new ApplicationForCrossFlow());
        add(new ApplicationForAstrophysics());
        add(new ApplicationForNarrowPattern());
        add(new ApplicationForECStrategy());
        add(new ApplicationForMCM());
        selected = this.getFirst();
    }

    /**
     * Appends the specified element to the end of this list.
     * <p>
     * <p>This method is equivalent to {@link #addLast}.
     *
     * @param socialForceApplication element to be appended to this list
     * @return {@code true} (as specified by {@link Collection#add})
     */
    @Override
    public boolean add(SocialForceApplication socialForceApplication) {
        boolean k = super.add(socialForceApplication);
        socialForceApplication.setApplicationListener(listener);
        return k;
    }

    private SocialForceApplication selected;

    public void select(SocialForceApplication application) {
        selected = application;
    }

    public SocialForceApplication current() {
        return selected;
    }
}
