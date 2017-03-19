package org.socialforce.neural;

import java.util.concurrent.CountDownLatch;

import org.joone.engine.FullSynapse;
import org.joone.engine.LinearLayer;
import org.joone.engine.Monitor;
import org.joone.engine.NeuralNetEvent;
import org.joone.engine.NeuralNetListener;
import org.joone.engine.SigmoidLayer;
import org.joone.engine.learning.TeachingSynapse;
import org.joone.io.MemoryInputSynapse;
import org.joone.net.NeuralNet;
import org.joone.samples.engine.xor.XOR;

/**
 * Created by sunjh1999 on 2017/3/20.
 */

public class NeuralNetwork implements NeuralNetListener {
    private Monitor monitor;
    private NeuralNet nnet;
    private CountDownLatch latch = new CountDownLatch(1);
    public static double XOR_INPUT[][] = { { 0.0, 0.0 }, { 1.0, 0.0 },
            { 0.0, 1.0 }, { 1.0, 1.0 } };
    private int epoch;

    public static double XOR_IDEAL[][] = { { 0.0 }, { 1.0 }, { 1.0 }, { 0.0 } };


    public NeuralNetwork()
    {
        // Firts, creates the three Layers
        LinearLayer	input = new LinearLayer();
        SigmoidLayer	hidden = new SigmoidLayer();
        SigmoidLayer	output = new SigmoidLayer();

        input.setLayerName("input");
        hidden.setLayerName("hidden");
        output.setLayerName("output");

        // sets their dimensions
        input.setRows(2);
        hidden.setRows(3);
        output.setRows(1);

        // Now create the two Synapses
        FullSynapse synapse_IH = new FullSynapse();	/* input -> hidden conn. */
        FullSynapse synapse_HO = new FullSynapse();	/* hidden -> output conn. */

        synapse_IH.setName("IH");
        synapse_HO.setName("HO");

        // Connect the input layer whit the hidden layer
        input.addOutputSynapse(synapse_IH);
        hidden.addInputSynapse(synapse_IH);

        // Connect the hidden layer whit the output layer
        hidden.addOutputSynapse(synapse_HO);
        output.addInputSynapse(synapse_HO);

        MemoryInputSynapse  inputStream = new MemoryInputSynapse();

        // The first two columns contain the input values
        inputStream.setInputArray(XOR_INPUT);
        inputStream.setAdvancedColumnSelector("1,2");

        // set the input data
        input.addInputSynapse(inputStream);

        TeachingSynapse trainer = new TeachingSynapse();

        // Setting of the file containing the desired responses provided by a FileInputSynapse
        MemoryInputSynapse samples = new MemoryInputSynapse();


        // The output values are on the third column of the file
        samples.setInputArray(XOR_IDEAL);
        samples.setAdvancedColumnSelector("1");
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
        System.out.println("Epoch: " + epoch + " Error:" + this.monitor.getGlobalError() );
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
    public static void main(String[] args)
    {
        NeuralNetwork xor = new NeuralNetwork();
        xor.run();
    }
}
