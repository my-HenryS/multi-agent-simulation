package org.socialforce.neural.impl;

/**
 * Created by sunjh1999 on 2017/5/9.
 */


import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.datavec.api.util.ClassPathResource;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.SplitTestAndTrain;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerStandardize;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;


public class RobustNeuralNetwork {

    private static Logger log = LoggerFactory.getLogger(RobustNeuralNetwork.class);

    public static void main(String[] args) throws Exception {

        //First: get the dataset using the record reader. CSVRecordReader handles loading/parsing
        int numLinesToSkip = 0;
        String parentPath = System.getProperty("user.dir")+"/resource/";
        String filePath = "output/MultiSetfast_7.csv";
        String delimiter = ",";

        String locationToSave = "neuralNet/new_7.net";

        RecordReader recordReader = new CSVRecordReader(numLinesToSkip,delimiter);
        recordReader.initialize(new FileSplit(new File(parentPath+filePath)));

        //Second: the RecordReaderDataSetIterator handles conversion to DataSet objects, ready for use in neural network
        int labelIndexFrom = 0;     //Row 0 to row 1 of each column REPRESENTS the labels
        int labelIndexTo = 1;
        int batchSize = 30647;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       ;    //Number of the used data

        DataSetIterator iterator = new RecordReaderDataSetIterator(recordReader, batchSize, labelIndexFrom, labelIndexTo, true);
        DataSet allData = iterator.next();
        allData.shuffle();
        SplitTestAndTrain testAndTrain = allData.splitTestAndTrain(0.9);  //Use 95% of data for training

        DataSet trainingData = testAndTrain.getTrain();
        DataSet testData = testAndTrain.getTest();

        //We need to normalize our data. We'll use NormalizeStandardize (which gives us mean 0, unit variance):
        DataNormalization normalizer = new NormalizerStandardize();
        normalizer.fit(trainingData);
        normalizer.transform(trainingData);
        normalizer.transform(testData);


        final int numInputs = 30;
        int outputNum = labelIndexTo - labelIndexFrom + 1;
        int iterations = 3000;
        int nEpoch = 80;
        long seed = 440;
        double rate = 0.1;
        double momentum = 0.9;

        log.info("Build model....");
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .seed(seed)
                .iterations(iterations)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .learningRate(rate)
                .weightInit(WeightInit.XAVIER)
                .updater(Updater.NESTEROVS).momentum(momentum)
                .list()
                .layer(0, new DenseLayer.Builder().nIn(numInputs).nOut(60)
                        .activation(Activation.RELU).build())
                .layer(1, new DenseLayer.Builder().nIn(60).nOut(60)
                        .activation(Activation.RELU).build())
                .layer(2, new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
                        .activation(Activation.IDENTITY)
                        .nIn(60).nOut(outputNum).build())
                .pretrain(false).backprop(true).build();

        //run the model
        MultiLayerNetwork model = new MultiLayerNetwork(conf);
        model.init();
        model.setListeners(new ScoreIterationListener(100));

        model.fit(trainingData);

        ModelSerializer.writeModel(model, parentPath+locationToSave, true );

        //evaluate the model on the test set
        INDArray output = model.output(testData.getFeatureMatrix());
        INDArray labels = testData.getLabels();
        for(int i = 0; i < testData.numExamples(); i++){ //取前100
            System.out.println(output.getRow(i)+" compare to ground truth: "+labels.getRow(i));
        }


    }

}