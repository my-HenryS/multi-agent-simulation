package org.socialforce.scene.impl;

import org.socialforce.app.*;
import org.socialforce.container.AgentPool;
import org.socialforce.container.EntityPool;
import org.socialforce.container.impl.LinkListAgentPool;
import org.socialforce.container.impl.LinkListEntityPool;
import org.socialforce.container.impl.LinkListPool;
import org.socialforce.drawer.Drawer;
import org.socialforce.drawer.impl.SceneDrawer;
import org.socialforce.geom.Box;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.model.*;
import org.socialforce.scene.Scene;
import org.socialforce.scene.SceneListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Ledenel on 2016/8/22.
 */
public class SimpleScene implements Scene {
    CountDownLatch latch = null;

    @Override
    public boolean addSceneListener(SceneListener sceneListener) {
        this.sceneListeners.add(sceneListener);
        return sceneListener.onAdded(this);
    }

    @Override
    public boolean removeSceneListener(SceneListener sceneListener) {
        return this.sceneListeners.remove(sceneListener);
    }

    List<SceneListener> sceneListeners = new ArrayList<>();

    /**
     *
     * set the drawer for the drawable.
     *
     * @param drawer the drawer.
     */
    @Override
    public void setDrawer(Drawer drawer) {
        this.drawer = (SceneDrawer) drawer;
    }

    public SimpleScene(Box bounds) {
        //设置scene的范围
        this.bounds = bounds;
        this.statics = new LinkListEntityPool();
        this.allAgents = new LinkListAgentPool();
    }

    protected Box bounds;
    protected SceneDrawer drawer;

    /**
     * get the current drawer the object is using.
     *
     * @return the drawer.
     */
    @Override
    public SceneDrawer getDrawer() {
        return drawer;
    }

    /**
     * interactionForce the next time step of the scene.
     * the time step will also forward 1 unit.
     */
    @Override
    public void stepNext() {
        LinkListPool<InteractiveEntity> entities = new LinkListPool<>();
        entities.addAll(statics);
        entities.addAll(allAgents);
        Iterable<InteractiveEntity> captors = entities.selectClass(Influential.class);
        model.fieldForce(allAgents);
        int size = 0;
        for(InteractiveEntity value : captors) {
            size++;
        }
        latch = new CountDownLatch(size);
        for (InteractiveEntity captor : captors){
            new thread(){
                public void run(){
                    Iterable<Agent> affectableAgents = allAgents.select(((Influential) captor).getView());
                    for (Agent target:affectableAgents){
                        ((Influential) captor).affect(target);
                    }
                    latch.countDown();
                }
            }.start();
        }
        try {
            latch.await();  //par-sync
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Iterable<InteractiveEntity> movables = entities.selectClass(Moveable.class);
        for (InteractiveEntity movable : movables) {
            ((Moveable)movable).act();
        }
        allAgents.removeIf(Agent::isEscaped);
        currentStep++;
        updateStep();
    }

    protected void updateStep() {
        for(SceneListener lis : sceneListeners){
            lis.onStep(this);
        }
    }

    public AgentEscapeListener getListener() {
        return listener;
    }

    public void setListener(AgentEscapeListener listener) {
        this.listener = listener;
    }

    public void setAllAgents(AgentPool allAgents) {
        this.allAgents = allAgents;
    }

    /**
     * Returns a string representation of the object. In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p>
     * The {@code toString} method for class {@code Object}
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `{@code @}', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Scene With Step " + currentStep +":"+ System.lineSeparator());
        for(Agent agent : allAgents) {
            sb.append(agent.toString());
            sb.append(System.lineSeparator());
        }
        sb.append("Scene End").append(System.lineSeparator());
        return sb.toString();
    }

    AgentPool allAgents;
    EntityPool statics;

    /**
     * get a set of agents the scene is managing.
     *
     * @return all agents.
     */
    @Override
    public AgentPool getAllAgents() {
        return allAgents;
    }

    /**
     * get a set of static entities the scene is managing.
     *
     * @return all static entitites.
     */
    @Override
    public EntityPool getStaticEntities() {
        return statics;
    }

    public void setStaticEntities(EntityPool statics) {
        this.statics = statics;
    }
    /**
     * get the bound of the scene.
     *
     * @return the bounds.
     */
    @Override
    public Box getBounds() {
        return bounds;
    }

    protected int currentStep = 0;

    /**
     * get current time step in this scene.
     *
     * @return the current step.
     */
    @Override
    public int getCurrentSteps() {
        return currentStep;
    }

    AgentEscapeListener listener;
    /**
     * triggers while an agent is escaped.
     *
     * @param agent the escaped agent.
     */
    @Override
    public void onAgentEscape(Agent agent) {
        if(listener != null) {
            listener.onAgentEscape(this,agent);
        }
        agent.escape();
    }

    Application application;
    @Override
    public Application getApplication() {
        return application;
    }

    @Override
    public void setApplication(Application application) {
        this.application = application;
    }

    //  2016/8/24 add visible settings and valueset.

    /**
     * judege if it is visible
     * @return
     */

    // added valueset settings.
    @Override
    public boolean isVisible() {
        return drawer.getDevice().isEnable();
    }

    /**
     * set the application be visible
     * @param visible
     */
    @Override
    public void setVisible(boolean visible) {
        if(visible) {
            drawer.getDevice().enable();
        } else {
            drawer.getDevice().disable();
        }
    }

    public Scene cloneWithBounds(){
        return new SimpleScene(bounds);
    }

    public Scene cloneWithStatics() {
        SimpleScene newscene = new SimpleScene(bounds);
        newscene.setStaticEntities((EntityPool) this.getStaticEntities().clone());
        return newscene;
    }

    @Override
    public void pack(){
        double xmin = Double.POSITIVE_INFINITY,xmax = Double.NEGATIVE_INFINITY,ymin = Double.POSITIVE_INFINITY,ymax = Double.NEGATIVE_INFINITY;
        Box bound;
        for(Agent agent : allAgents){
            bound = agent.getPhysicalEntity().getBounds();
            if (bound.getStartPoint().getX() < xmin){
                xmin = bound.getStartPoint().getX();
            }
            if (bound.getStartPoint().getY() < ymin){
                ymin = bound.getStartPoint().getY();
            }
            if (bound.getEndPoint().getX() > xmax){
                xmax = bound.getEndPoint().getX();
            }
            if (bound.getEndPoint().getY() > ymax){
                ymax = bound.getEndPoint().getY();
            }
        }
        for (InteractiveEntity entity : statics){
            bound = entity.getPhysicalEntity().getBounds();
            if (bound.getStartPoint().getX() < xmin){
                xmin = bound.getStartPoint().getX();
            }
            if (bound.getStartPoint().getY() < ymin){
                ymin = bound.getStartPoint().getY();
            }
            if (bound.getEndPoint().getX() > xmax){
                xmax = bound.getEndPoint().getX();
            }
            if (bound.getEndPoint().getY() > ymax){
                ymax = bound.getEndPoint().getY();
            }
        }
        this.bounds = new Box2D(new Point2D(xmin-5,ymin-5),new Point2D(xmax+5,ymax+5));
    }

    Model model;
    public Scene setModel(Model model){
        this.model = model;
        return this;
    }

    public Model getModel(){
        return model;
    }


    abstract class thread extends Thread {
        public abstract void run();
    }
}
