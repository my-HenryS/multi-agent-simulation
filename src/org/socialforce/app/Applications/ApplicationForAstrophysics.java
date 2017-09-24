package org.socialforce.app.Applications;

import org.socialforce.app.Interpreter;
import org.socialforce.app.impl.SimpleInterpreter;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.geom.impl.Velocity2D;
import org.socialforce.model.impl.SimpleForceModel;
import org.socialforce.model.impl.Star_Planet;
import org.socialforce.scene.Scene;
import org.socialforce.scene.SceneLoader;
import org.socialforce.scene.impl.*;

import java.io.InputStream;
import java.util.Iterator;

/**
 * Created by Whatever on 2017/3/3.
 */
public class ApplicationForAstrophysics extends SimpleApplication {
    /**
     * start the application immediately.
     */
    @Override
    public void start() {
        setUpScenes();
        for (Iterator<Scene> iterator = scenes.iterator(); iterator.hasNext(); ) {
            currentScene = iterator.next();
            this.initScene(currentScene);
            while (!toSkip()) {
                this.stepNext(currentScene);
            }
            if(onStop()) return;
        }
    }

    @Override
    public boolean toSkip() {
        return Skip || currentScene.getCurrentSteps() > 1000000;
    }


    @Override
    public void setUpScenes(){
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("empty.s");
        Interpreter interpreter = new SimpleInterpreter();
        interpreter.loadFrom(is);
        SceneLoader loader = interpreter.setLoader();
        loader.setModel(new SimpleForceModel(0.002));
        SimpleParameterPool parameters = new SimpleParameterPool();
        parameters.addValuesAsParameter(new SimpleEntityGenerator()
                .setValue(new Star_Planet(new Circle2D(new Point2D(0,0),6.3),new Velocity2D(0,-4)))
        );
        parameters.addValuesAsParameter(new SimpleEntityGenerator()
                .setValue(new Star_Planet(new Circle2D(new Point2D(50,0),5),new Velocity2D(0,8)))
        );
        parameters.addValuesAsParameter(new SimpleEntityGenerator()
                .setValue(new Star_Planet(new Circle2D(new Point2D(70,0),0.75),new Velocity2D(0,17)))
        );

        loader.readParameterSet(parameters);
        scenes = loader.readScene();
        for(Scene scene:scenes){
            scene.setApplication(this);
        }
    }
}
