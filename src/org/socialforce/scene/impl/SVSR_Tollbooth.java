package org.socialforce.scene.impl;
import org.socialforce.geom.ModelShape;
import org.socialforce.model.impl.SimpleSocialForceModel;
import org.socialforce.model.impl.SimpleTollbooth;
import org.socialforce.scene.Scene;
import org.socialforce.scene.SceneValue;

/**
 * Created by sunjh1999 on 2017/1/20.
 */
public class SVSR_Tollbooth implements SceneValue<SimpleTollbooth> {
    protected SimpleTollbooth tollbooth;
    protected String name;
    public SVSR_Tollbooth(ModelShape modelShape, double interval){this.tollbooth= new SimpleTollbooth(modelShape, interval);}
    public SVSR_Tollbooth(SimpleTollbooth tollbooth){
        this.tollbooth = tollbooth;
    }
    @Override
    public String getEntityName() {
        return name;
    }

    @Override
    public void setEntityName(String name) {
        this.name = name;
    }

    @Override
    public SimpleTollbooth getValue() {
        return tollbooth;
    }

    @Override
    public void setValue(SimpleTollbooth value) {
        this.tollbooth = value;
    }

    @Override
    public void apply(Scene scene) {
        tollbooth.setName("SimpleTollbooth");
        scene.getStaticEntities().add(tollbooth);
        tollbooth.setScene(scene);
        tollbooth.setModel(new SimpleSocialForceModel());
    }

    @Override
    public int compareTo(SceneValue<SimpleTollbooth> o) {
        return 0;
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public void setPriority(int priority) {

    }
}
