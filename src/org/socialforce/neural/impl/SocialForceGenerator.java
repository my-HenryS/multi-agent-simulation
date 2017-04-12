package org.socialforce.neural.impl;

import org.socialforce.geom.Point;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.model.impl.PsychologicalForceRegulation;
import org.socialforce.neural.Coordinates;
import org.socialforce.scene.Scene;
import org.socialforce.strategy.Path;
import org.socialforce.strategy.impl.AStarPath;

import javax.net.ssl.SSLContext;
import java.util.LinkedList;

/**
 * Created by sunjh1999 on 2017/3/31.
 */
public class SocialForceGenerator extends WallForceGenerator{
    LinkedList<double[][]> W = new LinkedList<>(); //权值矩阵
    double p = 3; //行人的影响系数
    double w = 1; //墙的影响系数
    double expectV = 6; //期望速度

    public SocialForceGenerator(double timestep, int intercept, double min_div) {
        super(timestep, intercept, min_div);
    }

    /**
     * 生成行人在每一时刻的位置矩阵
     */
    private void genW(){
        for(int t = 0; t < matrix.get(matrix.size()-1).size(); t++){
            double[][] tmp = new double[map.length][map[0].length];
            for(int i = 0; i < map.length; i++){
                for(int j = 0; j < map[i].length; j++){
                    tmp[i][j] = map[i][j] * w;
                }
            }
            for(LinkedList<Coordinates> positions: matrix){
                Coordinates position = positions.get(t); //找到第t时间的数据
                if(position != null){
                    int x = (int)((position.X() - dX)/min_div);
                    int y = (int)((position.Y() - dY)/min_div);
                    tmp[x][y] += p;
                }
            }
            W.add(tmp);
        }
    }

    private Coordinates getNext(Coordinates c){
        Point nextStep = path.nextStep(new Point2D(c.X(),c.Y()));
        nextStep.moveBy(-c.X(), -c.Y()).scaleBy(1/nextStep.length());
        return new Coordinates(nextStep.getX(), nextStep.getY());
    }

    /**
     * 行人在t时刻下的周围权值矩阵情况
     * @param c 行人坐标
     * @param t t时刻
     * @return
     */
    public double[] getSurrounding(Coordinates c, int t){
        int x = (int)((c.X() - dX)/min_div), y = (int)((c.Y() - dY)/min_div);  //计算坐标c在map中的坐标
        int range = (int)(this.range / min_div);   //计算map中的真实范围
        double[][] map = W.get(t);
        //计算影响范围
        double[] surroundings = new double[(range * 2 + 1) * (range * 2 + 1)];

        for(int i = x - range; i <= x + range; i++){
            for(int j = y - range; j <= y + range; j++){
                if(i >= 0 && j >= 0 && i < map.length && j < map[0].length)
                    surroundings[(i - (x - range))*(range * 2 + 1) + (j - (y - range))] = map[i][j];
            }
        }
        surroundings[surroundings.length/2] -= p; //抛去行人本身
        return surroundings;
    }

    @Override
    public void genOutput(Scene scene) {
        setMap(scene);
        genW();
        for (int i = 0 ; i < matrix.size() ; i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                if (available(i, j)) {
                    Coordinates prePoint = matrix.get(i).get(j);
                    double[] surroundings = getSurrounding(prePoint, j), tempA = new double[surroundings.length + 6];
                    Coordinates nextStep = getNext(prePoint), acc = calAcc(velocity.get(i).get(j + 1), velocity.get(i).get(j));
                    Coordinates velocity = this.velocity.get(i).get(j), nextV = this.velocity.get(i).get(j);
                    tempA[0] = acc.X();
                    tempA[1] = acc.Y();
                    tempA[2] = nextStep.X();
                    tempA[3] = nextStep.Y();
                    tempA[4] = velocity.X();
                    tempA[5] = velocity.Y();
                    for(int t = 0; t < surroundings.length; t++){
                        tempA[6 + t] = surroundings[t];
                    }
                    outputs.add(tempA);
                }
            }
        }
    }
}
