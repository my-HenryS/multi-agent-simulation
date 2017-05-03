package org.socialforce.app.gui.util;

import org.socialforce.app.Application;

/**
 * Created by Ledenel on 2017/3/3.
 */
public class ApplicationDisplayer {
    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public ApplicationDisplayer(Application application) {
        this.application = application;
    }

    public Application application;

    @Override
    public String toString() {
        return application.getName();
    }
}

