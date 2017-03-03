package org.socialforce.app.gui.util;

import org.socialforce.app.SocialForceApplication;

/**
 * Created by Ledenel on 2017/3/3.
 */
public class ApplicationDisplayer {
    public SocialForceApplication getApplication() {
        return application;
    }

    public void setApplication(SocialForceApplication application) {
        this.application = application;
    }

    public ApplicationDisplayer(SocialForceApplication application) {
        this.application = application;
    }

    public SocialForceApplication application;

    @Override
    public String toString() {
        return application.getName();
    }
}

