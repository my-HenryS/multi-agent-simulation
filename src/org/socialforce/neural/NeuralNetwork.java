package org.socialforce.neural;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.opencsv.CSVReader;
import com.sun.tools.javac.util.ArrayUtils;
import org.joone.engine.FullSynapse;
import org.joone.engine.LinearLayer;
import org.joone.engine.Monitor;
import org.joone.engine.NeuralNetEvent;
import org.joone.engine.NeuralNetListener;
import org.joone.engine.SigmoidLayer;
import org.joone.engine.learning.TeachingSynapse;
import org.joone.io.MemoryInputSynapse;
import org.joone.net.NeuralNet;

/**
 * Created by sunjh1999 on 2017/3/20.
 */

public class NeuralNetwork implements NeuralNetListener {
    private Monitor monitor;
    private NeuralNet nnet;
    private String directory = "/Users/sunjh1999/IdeaProjects/SocialForceSimulation/resource/trainset4.csv";
    private int labelIndexM = 2; //2以下
    private CountDownLatch latch = new CountDownLatch(1);
    public static double INPUT[][];
    private int epoch;

    public static double LABEL[][];


    public NeuralNetwork()
    {
        try {
            dataReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // First, creates the three Layers
        LinearLayer	input = new LinearLayer();
        SigmoidLayer	hidden = new SigmoidLayer();
        SigmoidLayer	output = new SigmoidLayer();

        input.setLayerName("input");
        hidden.setLayerName("hidden");
        output.setLayerName("output");

        // sets their dimensions
        input.setRows(24);
        hidden.setRows(30);
        output.setRows(2);

        // Now create the two Synapses
        FullSynapse synapse_IH = new FullSynapse();	/* input -> hidden conn. */
        FullSynapse synapse_HO = new FullSynapse();	/* hidden -> output conn. */

        synapse_IH.setName("IH");
        synapse_HO.setName("HO");

        // Connect the input layer with the hidden layer
        input.addOutputSynapse(synapse_IH);
        hidden.addInputSynapse(synapse_IH);

        // Connect the hidden layer whit the output layer
        hidden.addOutputSynapse(synapse_HO);
        output.addInputSynapse(synapse_HO);

        MemoryInputSynapse  inputStream = new MemoryInputSynapse();

        // The first two columns contain the input values
        inputStream.setInputArray(INPUT);
        inputStream.setAdvancedColumnSelector("1-24");

        // set the input data
        input.addInputSynapse(inputStream);

        TeachingSynapse trainer = new TeachingSynapse();

        // Setting of the file containing the desired responses provided by a FileInputSynapse
        MemoryInputSynapse samples = new MemoryInputSynapse();


        // The output values are on the third column of the file
        samples.setInputArray(LABEL);
        samples.setAdvancedColumnSelector("1-2");
        trainer.setDesired(samples);

        // Connects the Teacher to the last layer of the net
        output.addOutputSynapse(trainer);

        // Creates a new NeuralNet
        this.nnet = new NeuralNet();
        /*
         * All the layers must be inserted in the NeuralNet object
         */
        nnet.addLayer(input, NeuralNet.INPUT_LAYER);
        nnet.addLayer(hidden, NeuralNet.HIDDEN_LAYER);
        nnet.addLayer(output, NeuralNet.OUTPUT_LAYER);
        this.monitor = nnet.getMonitor();
        monitor.setTrainingPatterns(4);	// # of rows (patterns) contained in the input file
        monitor.setTotCicles(5000);	// How many times the net must be trained on the input patterns
        monitor.setLearningRate(0.7);
        monitor.setMomentum(0.3);
        monitor.setLearning(true);	// The net must be trained
        monitor.setSingleThreadMode(true);  // Set to false for multi-thread mode
        /* The application registers itself as monitor's listener so it can receive
          the notifications of termination from the net. */
        monitor.addNeuralNetListener(this);
    }

    public void run() {

        try {
            nnet.go(); // The net starts in async mode
            this.latch.await();


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cicleTerminated(NeuralNetEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void errorChanged(NeuralNetEvent e) {
        Monitor mon = (Monitor) e.getSource();
        if (mon.getCurrentCicle() % 100 == 0)
            System.out.println(" Epoch:  "
                    + (mon.getTotCicles() - mon.getCurrentCicle()) + "  RMSE: "
                    + mon.getGlobalError());
        epoch++;

    }

    @Override
    public void netStarted(NeuralNetEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void netStopped(NeuralNetEvent arg0) {
        this.latch.countDown();

    }

    @Override
    public void netStoppedError(NeuralNetEvent arg0, String arg1) {
        // TODO Auto-generated method stub

    }

    public void dataReader() throws IOException {
        File file = new File(directory);
        FileReader fReader = new FileReader(file);
        CSVReader csvReader = new CSVReader(fReader);
        List<String[]> sList = csvReader.readAll();
        LinkedList<double[]> inputs = new LinkedList<>(), labels = new LinkedList<>();
        for(String[] ss : sList){
            double[] lTemp = new double[labelIndexM], iTemp = new double[ss.length-labelIndexM];
            for(int i = 0 ; i < ss.length; i++){
                if(i < labelIndexM) lTemp[i] = Double.parseDouble(ss[i]);
                else iTemp[i-labelIndexM] = Double.parseDouble(ss[i]);
            }
            inputs.add(iTemp);
            labels.add(lTemp);
        }
        INPUT = new double[inputs.size()][inputs.get(0).length];
        LABEL = new double[labels.size()][labels.get(0).length];
        for(int i = 0; i < inputs.size(); i++){
            INPUT[i] = inputs.get(i);
            LABEL[i] = labels.get(i);
        }
    }

    public static void main(String[] args)
    {
        NeuralNetwork nnet = new NeuralNetwork();
        nnet.run();
    }
}
