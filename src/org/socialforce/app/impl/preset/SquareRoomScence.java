package org.socialforce.app.impl.preset;

import org.socialforce.app.Scene;
import org.socialforce.app.SocialForceApplication;
import org.socialforce.app.ValueSet;
import org.socialforce.container.AgentPool;
import org.socialforce.container.EntityPool;
import org.socialforce.drawer.Drawer;
import org.socialforce.geom.Box;
import org.socialforce.model.Agent;
import org.socialforce.model.PathFinder;

/**
 * Created by Whatever on 2016/8/31.
 */
public class SquareRoomScence implements Scene {
    @Override
    public void setDrawer(Drawer drawer) {
        
    }

    @Override
    public Drawer getDrawer() {
        return null;
    }

    @Override
    public void stepNext() {

    }

    @Override
    public AgentPool getAllAgents() {
        return null;
    }

    @Override
    public EntityPool getStaticEntities() {
        return null;
    }

    @Override
    public Box getBounds() {
        return null;
    }

    @Override
    public int getCurrentSteps() {
        return 0;
    }

    @Override
    public PathFinder getPathFinder() {
        return null;
    }

    @Override
    public void setPathFinder(PathFinder finder) {

    }

    @Override
    public void onAgentEscape(Agent agent) {

    }

    @Override
    public SocialForceApplication getApplication() {
        return null;
    }

    @Override
    public void setApplication(SocialForceApplication application) {

    }

    @Override
    public boolean isVisible() {
        return false;
    }

    @Override
    public void setVisible(boolean visible) {

    }

    @Override
    public ValueSet getValueSet() {
        return null;
    }
}
