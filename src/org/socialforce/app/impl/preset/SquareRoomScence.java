package org.socialforce.app.impl.preset;

import org.socialforce.app.Scene;
import org.socialforce.app.SocialForceApplication;
import org.socialforce.app.ValueSet;
import org.socialforce.container.AgentPool;
import org.socialforce.container.EntityPool;
import org.socialforce.drawer.Drawer;
import org.socialforce.geom.Box;
import org.socialforce.geom.Shape;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.model.Agent;
import org.socialforce.model.PathFinder;
import org.socialforce.model.impl.SafetyRegion;
import org.socialforce.model.impl.Wall;

/**
 * Created by Whatever on 2016/8/31.
 */
public class SquareRoomScence implements Scene {
    protected Wall[] walls;
    protected SafetyRegion[] safetyRegions;
    protected Agent[] agent;
    protected Box2D[] gates;

    public void setWall(Shape shape,int num){
        walls[num] = new Wall(shape);
    }

    public void setSafetyRegion(Shape shape,int num){
        safetyRegions[num] = new SafetyRegion(shape);
    }

    public void setAgent(Agent agent,int num){
        this.agent[num] = agent;
    }

    public void setGates(Box2D box2D,int num){
        gates[num] = box2D;
    }

    public Shape getWallShape(int num){
        return walls[num].getShape();
    }

    public Shape getSafetyRegionShape(int num){
        return safetyRegions[num].getShape();
    }


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
