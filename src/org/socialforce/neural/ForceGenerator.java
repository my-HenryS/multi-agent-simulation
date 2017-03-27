package org.socialforce.neural;

import com.opencsv.CSVReader;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by micha on 2017/3/27.
 */
public abstract class ForceGenerator implements DataSetGenerator{
    private int nearN = 5; //周围人数
    private double timestep = 0.5;  //步长、决定：位置差-速度关系、速度差-加速度关系
    private double height = 6.78;  //场景宽
    private int intercept = 4; //小数位数保留
    DecimalFormat formater = new DecimalFormat();
    private ArrayList<LinkedList<coordinates>> matrix;
    private ArrayList<ArrayList<coordinates>> velocity;
    LinkedList<double[]> outputs = new LinkedList<>();
    public void readFile(String directory){
        try{
            matrix = new ArrayList<LinkedList<coordinates>>();
            velocity = new ArrayList<ArrayList<coordinates>>();
            csv2matrix(directory);
            calcVelocity();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void csv2matrix(String directory)throws IOException {
        String axis[];
        LinkedList<coordinates> tempR = new LinkedList<coordinates>();
        File file = new File(directory);
        FileReader fReader = new FileReader(file);
        CSVReader csvReader = new CSVReader(fReader);
        List<String[]> lines = csvReader.readAll();
        for(String[] line : lines){
            tempR = new LinkedList<>();
            for(int i = 0 ; i < line.length ; i++){
                if(line[i] != null && line[i].length() > 0){
                    String templine = line[i].substring(1,line[i].length()-1);
                    axis = templine.split(",");
                    tempR.add(new coordinates(Double.parseDouble(axis[0])/10,Double.parseDouble(axis[1])/10));
                }
                else{
                    tempR.add(null);
                }
            }
            matrix.add(tempR);
        }
    }
    public void calcVelocity(){
        for(LinkedList<coordinates> tempR : matrix){
            ArrayList<coordinates> tempV = new ArrayList<>();
            for(coordinates tempT : tempR){
                if(tempT != null && tempR.indexOf(tempT)>=1 && tempR.get(tempR.indexOf(tempT)-1)!=null){
                    coordinates preT = tempR.get(tempR.indexOf(tempT)-1);
                    tempV.add(new coordinates((tempT.X()-preT.X())/timestep,(tempT.Y()-preT.Y())/timestep));
                }
                else{
                    tempV.add(new coordinates(0,0));
                }
            }
            velocity.add(tempV);
        }
    }
    public void toFile(String outDirect){
        File output_file = new File(outDirect);
        FileWriter fw= null;
        try {

            fw = new FileWriter(output_file);
            BufferedWriter bf=new BufferedWriter(fw);
            for(double [] output:outputs){
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
            double[] output = outputs.get(i);
            for(int j = 0; j < output.length; j++){
                matrix[i][j] = Double.parseDouble(formater.format(output[j]));
            }
        }
        return matrix;
    }
}
