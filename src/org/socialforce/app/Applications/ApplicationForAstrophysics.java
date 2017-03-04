package org.socialforce.app.Applications;

import org.socialforce.app.Interpreter;
import org.socialforce.app.impl.SimpleInterpreter;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.geom.impl.Velocity2D;
import org.socialforce.model.impl.Star_Planet;
import org.socialforce.model.impl.Wall;
import org.socialforce.scene.ParameterPool;
import org.socialforce.scene.Scene;
import org.socialforce.scene.SceneLoader;
import org.socialforce.scene.impl.*;
import org.socialforce.strategy.GoalStrategy;
import org.socialforce.strategy.PathFinder;
import org.socialforce.strategy.impl.AStarPathFinder;
import org.socialforce.strategy.impl.NearestGoalStrategy;

import java.io.InputStream;
import java.util.Iterator;

import static org.socialforce.scene.SceneLoader.genParameter;

/**
 * Created by Whatever on 2017/3/3.
 */
public class ApplicationForAstrophysics extends SimpleApplication {
    /**
     * start the application immediately.
     */
    @Override
    public void start() {
        for (Iterator<Scene> iterator = scenes.iterator(); iterator.hasNext(); ) {
            Scene scene = iterator.next();
            while (scene.getCurrentSteps() < 1000000) {
                scene.stepNext();
            }
        }
    }

    @Override
    public void setUpScenes(){
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("empty.s");
        Interpreter interpreter = new SimpleInterpreter();
        interpreter.loadFrom(is);
        SceneLoader loader = interpreter.setLoader();
        ParameterPool parameters = new SimpleParameterPool();
        parameters.addLast(genParameter(new SV_Star(new Star_Planet(new Circle2D(new Point2D(0,0),6.3)),new Velocity2D(0,-4))));
        parameters.addLast(genParameter(new SV_Star(new Star_Planet(new Circle2D(new Point2D(50,0),5)),new Velocity2D(0,8))));
        parameters.addLast(genParameter(new SV_Star(new Star_Planet(new Circle2D(new Point2D(70,0),0.75)),new Velocity2D(0,17))));
        //parameters.addLast(genParameter(new SV_Star(new Star_Planet(new Circle2D(new Point2D(90,0),4)),new Velocity2D(0,6))));
        loader.readParameterSet(parameters);
        scenes = loader.readScene(this);
    }
}
