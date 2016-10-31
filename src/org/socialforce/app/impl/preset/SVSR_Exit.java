package org.socialforce.app.impl.preset;

import org.socialforce.app.Scene;
import org.socialforce.app.SceneValue;
import org.socialforce.app.StaticSceneValue;
import org.socialforce.geom.ClippableShape;
import org.socialforce.geom.ClipperShape;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.impl.Wall;

/**
 * 代表一个可变的缺口（出口）的一个具体取值。
 * 该场景变量以name作为场景中的实体名，
 * 以一个ClipperShape来规定出口的位置和大小。
 */
public class SVSR_Exit implements SceneValue<ClipperShape[]> {
    protected ClipperShape[] exit;
    protected String name;

    /**
     * 获取所关联的实体名称。
     *
     * @return 场景中的实体名称。
     */
    @Override
    public String getEntityName() {
        return name;
    }

    /**
     * 设置获取所关联的实体名称。
     *
     * @param name 要设置的实体名称。
     */
    @Override
    public void setEntityName(String name) {
        this.name = name;
    }

    /**
     * 获得该场景参数赋值的值。
     *
     * @return 返回的具体值。
     */
    @Override
    public ClipperShape[] getValue() {
        return exit;
    }

    /**
     * 设置改场景参数赋值的值。
     *
     * @param value 要设置的值。
     */
    @Override
    public void setValue(ClipperShape[] value) {
        exit = value;
    }

    /**
     * 在墙上切出门
     * 如果墙宽度为零则什么都不做
     * 目前门的参考点必须在墙内
     * select(Point)偶尔会导致诡异的空指针问题,可以考虑尝试更加诡异的造一个0半径的圆
     * @param scene 要被更改的场景。
     */
    @Override
    public void apply(Scene scene) {
/*        for (int i = 0;i < exit.length;i++){
            if (exit[i].getBounds().getStartPoint().getX() != exit[i].getBounds().getEndPoint().getX()
                 && exit[i].getBounds().getStartPoint().getY() != exit[i].getBounds().getEndPoint().getY()) {
             Wall wall = (Wall) scene.getStaticEntities().selectTop((Point2D) exit[i].getReferencePoint());
              wall.setShape(exit[i].clip((ClippableShape) wall.getShape()));
         }
         else //do nothing;
    }*/
    }

    @Override
    public int compareTo(SceneValue<ClipperShape[]> o) {
        return 0;
    }


    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public void setPriority(int priority) {
        this.priority = priority;
    }

    private int priority;


}
