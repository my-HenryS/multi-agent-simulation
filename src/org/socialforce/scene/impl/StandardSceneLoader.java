package org.socialforce.scene.impl;

import org.socialforce.app.*;
import org.socialforce.model.impl.SimpleSocialForceModel;
import org.socialforce.model.impl.Wall;
import org.socialforce.scene.*;

import java.io.File;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;

import static org.socialforce.scene.SceneGenerator.generate;

/**
 * Created by sunjh1999 on 2017/1/14.
 */
public class StandardSceneLoader implements SceneLoader {
    protected ParameterPool parameterSet;
    protected SocialForceApplication application;
    protected Wall[] walls = new Wall[]{};
    Scene scene;
    public StandardSceneLoader(Scene scene, Wall[] walls){
        this.scene = scene;
        this.walls = walls;
        this.parameterSet = new SimpleParameterPool();
    }
    @Override
    public void setSource(InputStream stream) {

    }

    @Override
    public void setSource(File file) {

    }

    public Scene staticScene() {
        Scene new_scene = scene.simpleclone();
        Wall[] new_walls = new Wall[walls.length];
        for(int i = 0; i < walls.length; i++){
            new_walls[i] = walls[i].standardclone();
        }
        for (int i = 0;i < new_walls.length;i++) {
            new_walls[i].setName("wall" + i);
            new_scene.getStaticEntities().add(new_walls[i]);
            new_walls[i].setScene(new_scene);
            new_walls[i].setModel(new SimpleSocialForceModel());
        }
        return new_scene;
    }

    @Override
    public LinkedList<Scene> readScene(SocialForceApplication application) {
        LinkedList<Scene> scenes = new LinkedList<>();
        int total_num = 1;
        //计算所有可能情况
        for (Iterator<SceneParameter> iterator = parameterSet.iterator(); iterator.hasNext();){
            SimpleSceneParameter parameter = (SimpleSceneParameter) iterator.next();
            total_num = total_num * parameter.getParameter().size();
        }
        //遍历 -- 全组合问题
        for(int i = 0; i < total_num; i++){
            ValueSet values = new SimpleValueSet();
            for (Iterator<SceneParameter> iterator = parameterSet.iterator(); iterator.hasNext();){
                SimpleSceneParameter parameter = (SimpleSceneParameter) iterator.next();
                values.add(parameter.getParameter().get(i%parameter.getParameter().size()));   //根据相对周长的偏移量计算当前应添加的SceneValue
            }
            Scene scene = staticScene();
            scene = generate(scene,values);
            scene.setApplication(application);
            scene.pack();
            scenes.addLast(scene);
        }
        return scenes;
    }

    @Override
    public ParameterPool readParameterSet(ParameterPool parameterSet) {
        this.parameterSet = parameterSet;
        return parameterSet;
    }

    @Override
    public ParameterPool readValueSet(ValueSet values) {
        for(Iterator<SceneValue> iterator = values.iterator(); iterator.hasNext();){
            SceneValue sceneValue = iterator.next();
            SceneParameter parameter = new SimpleSceneParameter();
            parameter.addValue(sceneValue);
            parameterSet.addLast(parameter);
        }
        return parameterSet;
    }

}

