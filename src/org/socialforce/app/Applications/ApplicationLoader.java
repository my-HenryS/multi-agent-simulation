package org.socialforce.app.Applications;

import org.socialforce.app.ApplicationListener;
import org.socialforce.app.Application;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by Ledenel on 2017/3/2.
 */
public class ApplicationLoader extends LinkedList<Application> implements Collection<Application> {
    private ApplicationListener listener;

    /**
     * 使用一个指定的ApplicationListener构造一个loader。
     * 使用如下的预设场景办法。
     */
    public ApplicationLoader(ApplicationListener listener) {
        super();
        this.listener = listener;
        add(new ApplicationForCanteen());
        add(new ApplicationForEllipse());
        add(new ApplicationForDoorTest());
        add(new ApplicationForCrossFlow());
        add(new ApplicationForAstrophysics());
        add(new ApplicationForNarrowPattern());
        add(new ApplicationForECStrategy());
        add(new ApplicationForECTest());
        add(new ApplicationForMCM());
        add(new ApplicationForTurnDoorTest());
        add(new ApplicationModelingReal());
        selected = this.getLast();
    }

    /**
     * Appends the specified element to the end of this list.
     * <p>
     * <p>This method is equivalent to {@link #addLast}.
     *
     * @param application element to be appended to this list
     * @return {@code true} (as specified by {@link Collection#add})
     */
    @Override
    public boolean add(Application application) {
        boolean k = super.add(application);
        application.setApplicationListener(listener);
        return k;
    }

    private Application selected;

    public void select(Application application) {
        selected = application;
    }

    public Application current() {
        return selected;
    }
}
