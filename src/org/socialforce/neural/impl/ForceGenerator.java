package org.socialforce.neural.impl;

import com.opencsv.CSVReader;
import org.socialforce.geom.Point;
import org.socialforce.geom.Vector;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.geom.impl.Vector2D;
import org.socialforce.neural.DataSetGenerator;

import java.io.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by micha on 2017/3/27.
 */
public abstract class ForceGenerator implements DataSetGenerator {
    protected double timestep;  //步长、决定：位置差-速度关系、速度差-加速度关系
    DecimalFormat formater = new DecimalFormat();
    protected ArrayList<LinkedList<Point2D>> matrix;//matrix存的是所有人的位置数据
    protected ArrayList<ArrayList<Vector2D>> velocity;//velocity存的是所有人的速度数据
    LinkedList<Double[]> outputs = new LinkedList<>();
    private String superPath = System.getProperty("user.dir")+"/resource/";


    public ForceGenerator(double timestep, int intercept){
        this.timestep = timestep;
        formater.setMaximumFractionDigits(intercept);
        formater.setGroupingSize(0);
        formater.setRoundingMode(RoundingMode.HALF_UP);
    }

    public void readFile(String directory, int timeInterval){
        matrix =  new ArrayList<>();
        velocity = new ArrayList<>();
        try{
            csv2matrix(superPath + directory, timeInterval);
            calcVelocity();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void csv2matrix(String directory, int timeInterval)throws IOException {
        String axis[];
        LinkedList<Point2D> tempR;
        File file = new File(directory);
        FileReader fReader = new FileReader(file);
        CSVReader csvReader = new CSVReader(fReader);
        List<String[]> lines = csvReader.readAll();//每行是一个人的所有时刻的数据
        for(String[] line : lines){
            tempR = new LinkedList<>();
            for(int i = 0 ; i < line.length ; i++){
                if(i % timeInterval != 0) continue;
                if(line[i] != null && line[i].length() > 0){
                    String templine = line[i].substring(0,line[i].length());//去括号
                    axis = templine.split(",");//获得每一个单元格的x,y，存在axis[]数组中
                    tempR.add(new Point2D(Double.parseDouble(axis[0]),Double.parseDouble(axis[1])));//tempR存的是每一个人的所有位置数据 561652165
                //快读需要进行坐标转换0.935
                }
                else{
                    tempR.add(null);
                }
            }
            matrix.add(tempR);//matrix存的是所有人的位置数据
        }
    }

    public void calcVelocity(){
        for(LinkedList<Point2D> tempR : matrix){
            ArrayList<Vector2D> tempV = new ArrayList<>();
//            for(Point2D tempT : tempR){
//                if(tempT != null && tempR.indexOf(tempT)+1 < tempR.size() && tempR.get(tempR.indexOf(tempT)+1)!=null){
//                    Point2D nexT = tempR.get(tempR.indexOf(tempT)+1);
//                    tempV.add(new Vector2D((nexT.getX()-tempT.getX())/timestep,(nexT.getY()-tempT.getY())/timestep));
//                }
//                else{
//                    tempV.add(null);
//                }
//            }
                for(int i=0;i<tempR.size();i++){
                    Point2D tempT=tempR.get(i);
                if(tempT != null && i+1 < tempR.size() && tempR.get(i+1)!=null){
                    Point2D nexT = tempR.get(i+1);
                    tempV.add(new Vector2D((nexT.getX()-tempT.getX())/timestep,(nexT.getY()-tempT.getY())/timestep));
                }
                else{
                    tempV.add(null);
                }
            }
            velocity.add(tempV);
        }
    }

    /**
     * 判断i人在j时刻是否可以作为训练集
     * 即：
     *     当前时刻和下一时刻可以计算速度
     * @param i
     * @param j
     * @return
     */
    public boolean available(int i, int j){
        return matrix.get(i).get(j) != null /*这项可以不填*/ && velocity.get(i).get(j) != null && j+1 < velocity.get(i).size() && velocity.get(i).get(j+1) != null;
    }

    public void toFile(String outDirect){
        File output_file = new File(outDirect);
        FileWriter fw= null;
        try {

            fw = new FileWriter(output_file);
            BufferedWriter bf=new BufferedWriter(fw);
            for(Double [] output:outputs){
                String s = "";
                for(double element:output){
                    s += String.valueOf(formater.format(element))+",";
                }
                bf.write(s.substring(0,s.length()-1)+"\n");
            }
            bf.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toFile(String outDirect, int interval){
        File output_file = new File(superPath+outDirect);
        FileWriter fw= null;
        int count = 0;
        try {

            fw = new FileWriter(output_file);
            BufferedWriter bf=new BufferedWriter(fw);
            for(Double [] output:outputs){
                count ++;
                if(count % interval != 0)
                    continue;
                String s = "";
                for(double element:output){
                    s += String.valueOf(formater.format(element))+",";
                }
                bf.write(s.substring(0,s.length()-1)+"\n");
            }
            bf.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double[][] toMatrix() {
        double[][] matrix = new double[outputs.size()][outputs.get(0).length];
        for(int i = 0; i < outputs.size(); i++){
            Double[] output = outputs.get(i);
            for(int j = 0; j < output.length; j++){
                matrix[i][j] = Double.parseDouble(formater.format(output[j]));
            }
        }
        return matrix;
    }
}
