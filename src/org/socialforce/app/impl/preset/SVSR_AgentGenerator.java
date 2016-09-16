package org.socialforce.app.impl.preset;

import org.socialforce.app.SceneValue;
import org.socialforce.app.StaticSceneValue;
import org.socialforce.geom.Box;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.model.Agent;
import org.socialforce.model.InteractiveEntity;

/**
 * Created by Whatever on 2016/9/16.
 */
public class SVSR_AgentGenerator extends StaticSceneValue<SVSR_AgentGenerator.AgentGenerator> implements SceneValue<SVSR_AgentGenerator.AgentGenerator> {

    protected class AgentGenerator{
        protected double X_distance,Y_distance,Z_distance;
        protected Box Area;

        public AgentGenerator(double X_distance,double Y_distance,double Z_distance,Box Area){
            this.X_distance = X_distance;
            this.Y_distance = Y_distance;
            this.Z_distance = Z_distance;
            this.Area = Area;
        }

        public double getX_distance(){
            return X_distance;
        }
        public double getY_distance(){
            return Y_distance;
        }
        public double getZ_distance(){
            return Z_distance;
        }
        public Box getArea(){
            return Area;
        }
    }

    /**
     * 往一片区域里放agent
     * @param entity 要在可放区里面放的Agent类型
     */
    @Override
    public void applyEach(InteractiveEntity entity) {
        Agent agent = (Agent)entity;
        if (value.Area instanceof Box2D) {
            for (int i = 0; i < (value.Area.getStartPoint().getX() - value.Area.getEndPoint().getX()) / value.X_distance; i++) {
                for (int j = 0; j < (value.Area.getStartPoint().getY() - value.Area.getEndPoint().getY()) / value.Y_distance; j++) {
                    ((Agent) entity).act();//TODO 这里有点尴尬，因为Agent接口里没有怎么搁之类的事情，连clone也没有，姑且先act。
                }
            }
        }
        else throw new IllegalArgumentException("暂未实现非二维的生成区块");
    }


    private int priority;

    @Override
    public int compareTo(SceneValue<AgentGenerator> o) {
        return o.getPriority() - this.getPriority();
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public void setPriority(int priority) {
        this.priority = priority;
    }
}
