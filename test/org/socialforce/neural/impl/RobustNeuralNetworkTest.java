package org.socialforce.neural.impl;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.deeplearning4j.util.ModelSerializer;
import org.junit.Test;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by sunjh1999 on 2017/6/17.
 */
public class RobustNeuralNetworkTest {
    String parentPath = System.getProperty("user.dir")+"/resource/";

    String locationToSave = "neuralNet/robust.net";
    @Test
    public void loadmodel(){
        MultiLayerNetwork model = null;


        try {
            model = ModelSerializer.restoreMultiLayerNetwork(parentPath+locationToSave);
        } catch (IOException e) {
            e.printStackTrace();
        }

        double v = 1.5;
        for(int i = 0; i < 150;i++) {
            INDArray input = Nd4j.create(new double[]{0.001, v, 0, 0, 0, 0, 0, 0.9, 0, 0, 0});
            INDArray output = model.output(input);
            System.out.println(output.toString());
            v = Math.sqrt(Math.pow(output.getDouble(0),2) + Math.pow(output.getDouble(1),2));
        }
    }

}