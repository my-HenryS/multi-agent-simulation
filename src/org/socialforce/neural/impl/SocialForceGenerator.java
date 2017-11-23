package org.socialforce.neural.impl;

import org.socialforce.geom.impl.Point2D;
import org.socialforce.geom.impl.Point2Dcompare;
import org.socialforce.geom.impl.Vector2D;
import org.socialforce.scene.Scene;


import java.util.Collections;
import java.util.LinkedList;
import java.util.ArrayList;
/**
 * Created by sunjh1999 on 2017/3/31.
 */
public class SocialForceGenerator extends WallForceGenerator{
    LinkedList<double[][]> W; //权值矩阵，每一个时刻的加权map（全）
    double p = 0.3; //行人的影响系数
    double w = 0.1; //墙的影响系数
    double expectV = 6; //期望速度
    double range;

    public SocialForceGenerator(double timestep, int intercept, double min_div) {
        super(timestep, intercept, min_div);
        this.range = min_div;
    }

    /**
     * 生成行人在每一时刻的位置矩阵
     */
    private void genW(){
        W = new LinkedList<>();
        for(int t = 0; t < matrix.get(matrix.size()-1).size(); t++){
            double[][] tmp = new double[map.length][map[0].length];
            for(int i = 0; i < map.length; i++){
                for(int j = 0; j < map[i].length; j++){
                    tmp[i][j] = map[i][j] * w;
                }
            }
            for(LinkedList<Point2D> positions: matrix){
                Point2D position = positions.get(t); //找到第t时间的数据
                if(position != null){
                    int x = (int)((position.getX() - dX)/min_div);
                    int y = (int)((position.getY() - dY)/min_div);
                    if(velocity.get(matrix.indexOf(positions)).get(t)!=null)
                    tmp[x][y] += p*(velocity.get(matrix.indexOf(positions)).get(t).length());
                }
            }
            W.add(tmp);
        }
    }

    private Vector2D getNext(Point2D c){
        Point2D nextStep = (Point2D) path.nextStep(new Point2D(c.getX(),c.getY()));
        nextStep.moveBy(-c.getX(), -c.getY()).scaleBy(1/nextStep.length());
        return nextStep;
    }

    /**
     * 行人在t时刻下的周围权值矩阵情况 //行人在t时刻下周围人与自己的相对位置速度情况
     * @param c 行人坐标
     * @param t t时刻
     * @return
     */
    public double[] getSurrounding(Point2D c, int t){
        int x = (int)((c.getX() - dX)/min_div), y = (int)((c.getY() - dY)/min_div);  //计算坐标c在map中的坐标
        int range = (int)(this.range / min_div);   //计算map中的真实范围 上下左右各1
        double[][] map = W.get(t);
        //计算影响范围
        double[] surroundings = new double[(range * 2 + 1) * (range * 2 + 1)];

        for(int i = x - range; i <= x + range; i++){
            for(int j = y - range; j <= y + range; j++){
                if(i >= 0 && j >= 0 && i < map.length && j < map[0].length)
                    surroundings[(i - (x - range))*(range * 2 + 1) + (j - (y - range))] = map[i][j];
            }
        }
        surroundings[surroundings.length/2] = 0; //抛去行人本身
        return surroundings;
    }
    /**
     * 行人在t时刻下周围人与自己的相对位置速度情况
     * @param i 行人坐标
     * @param t t时刻
     * @return
     */
    public double[] getNeighbor(int i, int t){
        double[] neighbor=new double[20];
        ArrayList<Double> neighbor1=new ArrayList<Double>();
        Point2D prePoint = matrix.get(i).get(t);
        Vector2D thisVelocity = this.velocity.get(i).get(t).clone();
        ArrayList<Point2Dcompare> delpoints=new ArrayList<>();
        for(int c=0;c<matrix.size();c++){
            if(c==i)
                continue;
            if(available(c,t)){
                Point2Dcompare temp=new Point2Dcompare(c,matrix.get(c).get(t).getX()-prePoint.getX(),matrix.get(c).get(t).getY()-prePoint.getY());
                delpoints.add(temp);
            }
        }

        Collections.sort(delpoints);
        int time=0;
        for(Point2Dcompare delpoint:delpoints){     //排列方式delx,dely,delvx,delvy
            if(time==5)
                break;
            /*以下是对三米之内的行人进行归一化处理
            */
            if(delpoint.getX()>=0) {
                if(delpoint.getX()>3){
                    delpoint.setX(0);
                }else{
                    delpoint.setX(1-delpoint.getX()/3);
                }
            }else{
                if(delpoint.getX()<-3){
                    delpoint.setX(0);
                }else{
                    delpoint.setX(-(1+delpoint.getX()/3));
                }
            }

            if(delpoint.getY()>=0) {
                if(delpoint.getY()>3){
                    delpoint.setY(0);
                }else{
                    delpoint.setY(1-delpoint.getY()/3);
                }
            }else{
                if(delpoint.getY()<-3){
                    delpoint.setY(0);
                }else{
                    delpoint.setY(-(1+delpoint.getY()/3));
                }
            }
            //以上是对三米之内的行人进行归一化处理
            neighbor1.add(delpoint.getX());
            neighbor1.add(delpoint.getY());
            int index=delpoint.getIndex();
            Vector2D otherVelocity=this.velocity.get(index).get(t).clone();
            boolean toofar=(delpoint.getX()==0)||(delpoint.getY()==0);
            neighbor1.add(toofar?0:otherVelocity.getX()-thisVelocity.getX());
            neighbor1.add(toofar?0:otherVelocity.getY()-thisVelocity.getY());
            time=time+1;
        }
        for(int s=0;s<neighbor1.size();s++){
            neighbor[s]=neighbor1.get(s);
        }
        return neighbor;
    }
    public void genOutput(Scene scene) {
        setMap(scene);

        for (int i = 0 ; i < matrix.size() ; i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                if (available(i, j)) {
                    Point2D prePoint = matrix.get(i).get(j);
                    double[] neighbor = getNeighbor(i, j);
                //    Vector2D nextStep = getNext(prePoint);
                    Vector2D nextStep;
                //    Vector2D acc = calAcc(velocity.get(i).get(j + 1), velocity.get(i).get(j));
                    Vector2D thisVelocity = this.velocity.get(i).get(j).clone(), nextVelocity = this.velocity.get(i).get(j + 1).clone();
                    nextStep = getNext(prePoint);

//                      /* 获取旋转角并旋转所有输入输出向量 */
                    double angle = Vector2D.getRotateAngle(new Vector2D(1,0), thisVelocity);
                    nextVelocity.rotate(angle);
                    nextStep.rotate(angle);
                    thisVelocity.rotate(angle);
                    rotateNeighbor(neighbor,angle);


                    if(nextStep.getY() < 0){
                        nextStep = new Vector2D(nextStep.getX(),-nextStep.getY());
                        nextVelocity = new Vector2D(nextVelocity.getX(), -nextVelocity.getY());
                        for(int t = 1; t < neighbor.length; t+=2){
                            neighbor[t] = - neighbor[t];
                        }
                    }


                    LinkedList<Double>tempA = new LinkedList<>();
                    //tempA.add((double)i);
                    //tempA.add((double)j);
                    //tempA.add(prePoint.getX());
                    //tempA.add(prePoint.getY());

                    tempA.add(nextVelocity.getX()); //下一步长速度x轴
                    tempA.add(nextVelocity.getY()); //下一步长速度y轴

                    tempA.add(Vector2D.getRotateAngle(nextStep,new Vector2D(1,0)));  //A*方向和速度夹角
                    tempA.add(thisVelocity.getX());   //速度大小 （已旋转至x正向）
                    for(int t = 0; t < neighbor.length; t++){
                        tempA.add(neighbor[t]);
                    }
                    outputs.add(tempA.toArray(new Double[tempA.size()]));
                }
            }
        }
    }

    public void genOutput_old(Scene scene) {
        setMap(scene);
        genW();
        for (int i = 0 ; i < matrix.size() ; i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                if (available(i, j)) {
                    Point2D prePoint = matrix.get(i).get(j);
                    double[] surroundings = getSurrounding(prePoint, j);
                    Vector2D nextStep = getNext(prePoint), acc = calAcc(velocity.get(i).get(j + 1), velocity.get(i).get(j));
                    Vector2D thisVelocity = this.velocity.get(i).get(j).clone(), nextVelocity = this.velocity.get(i).get(j + 1).clone();
                    nextVelocity = acc;
                    /* 获取旋转角并旋转所有输入输出向量 */
                    double angle = Vector2D.getRotateAngle(new Vector2D(1,0), thisVelocity);
                    nextVelocity.rotate(angle);
                    nextStep.rotate(angle);
                    thisVelocity.rotate(angle);

                    /* 旋转输入矩阵 */
                    long rotateNum = Math.round(angle/(Math.PI/4));
                    for(;rotateNum > 0;rotateNum--){
                        rotateMatrix(surroundings);
                    }

                    /* 翻转 */
                    if(nextStep.getY() < 0){
                        nextStep = new Vector2D(nextStep.getX(),-nextStep.getY());
                        nextVelocity = new Vector2D(nextVelocity.getX(), -nextVelocity.getY());
                        upSideDownAMatrix(surroundings);
                    }

                    LinkedList<Double>tempA = new LinkedList<>();
                    tempA.add(nextVelocity.getX()); //加速度x轴
                    tempA.add(nextVelocity.getY()); //加速度y轴
                    tempA.add(Vector2D.getRotateAngle(nextStep, new Vector2D(1,0)));  //A*方向和速度夹角
                    tempA.add(thisVelocity.getX());   //速度大小 （已旋转至x正向）
                    //tempA[5] = thisVelocity.getY();  旋转之后恒为0
                    for(int t = 0; t < surroundings.length; t++){
                        tempA.add(surroundings[t]);
                    }
                    outputs.add(tempA.toArray(new Double[tempA.size()]));
                }
            }
        }
    }

    /**
     * 逆时针旋转一次W矩阵
     * @param surroundings
     */
    private void rotateMatrix(double[] surroundings){
        int[] nextV = new int[]{1,2,5,0,4,8,3,6,7};
        double[] temp = surroundings.clone();
        for(int i = 0; i < 9; i++){
            surroundings[i] = temp[nextV[i]];
        }
    }

    /**
     * 翻转一次W矩阵
     * @param surroundings
     */
    private void upSideDownAMatrix(double[] surroundings){
        int[] nextV = new int[]{6,7,8,3,4,5,0,1,2};
        double[] temp = surroundings.clone();
        for(int i = 0; i < 9; i++){
            surroundings[i] = temp[nextV[i]];
        }
    }
    /**
     * 翻转一次邻居矩阵
     * @param neighbor
     */
    public void rotateNeighbor(double[] neighbor,double angle){

        for(int i=0;i<neighbor.length;i=i+2){
            Vector2D tempv=new Vector2D(neighbor[i],neighbor[i+1]);
            tempv.rotate(angle);
            neighbor[i]=tempv.getX();
            neighbor[i+1]=tempv.getY();
        }


    }

}
