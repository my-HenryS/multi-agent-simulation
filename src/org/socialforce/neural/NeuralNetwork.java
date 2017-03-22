package org.socialforce.neural;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.opencsv.CSVReader;
import com.sun.tools.javac.util.ArrayUtils;
import org.joone.engine.*;
import org.joone.engine.learning.TeachingSynapse;
import org.joone.io.MemoryInputSynapse;
import org.joone.io.MemoryOutputSynapse;
import org.joone.net.NeuralNet;
import org.joone.net.NeuralNetLoader;

/**
 * Created by sunjh1999 on 2017/3/20.
 */

public class NeuralNetwork implements NeuralNetListener {
    private Monitor monitor;
    private double []lMax ,lMin;
    private NeuralNet nnet;
    private String directory = "/Users/sunjh1999/IdeaProjects/SocialForceSimulation/resource/trainset4.csv";
    private int labelIndexM = 2; //2以下
    private CountDownLatch latch = new CountDownLatch(1);
    public double INPUT[][];
    private int epoch = 100;

    public double LABEL[][];


    public NeuralNetwork() {
        try {
            dataReader(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // First, creates the three Layers
        LinearLayer input = new LinearLayer();
        SigmoidLayer hidden = new SigmoidLayer();
        SigmoidLayer hidden2 = new SigmoidLayer();
        LinearLayer output = new LinearLayer();

        input.setLayerName("input");
        hidden.setLayerName("hidden");
        hidden2.setLayerName("hidden2");
        output.setLayerName("output");

        // sets their dimensions
        input.setRows(24);
        hidden.setRows(50);
        hidden2.setRows(50);
        output.setRows(2);

        // Now create the two Synapses
        FullSynapse synapse_IH = new FullSynapse();	/* input -> hidden conn. */
        FullSynapse synapse_H2H = new FullSynapse();	/* hidden -> hidden2 conn. */
        FullSynapse synapse_HO = new FullSynapse();	/* hidden -> output conn. */

        synapse_IH.setName("IH");
        synapse_H2H.setName("H2H");
        synapse_HO.setName("HO");

        // Connect the input layer with the hidden layer
        input.addOutputSynapse(synapse_IH);
        hidden.addInputSynapse(synapse_IH);

        // Connect the input layer with the hidden layer
        hidden.addOutputSynapse(synapse_H2H);
        hidden2.addInputSynapse(synapse_H2H);

        // Connect the hidden layer whit the output layer
        hidden2.addOutputSynapse(synapse_HO);
        output.addInputSynapse(synapse_HO);

        MemoryInputSynapse inputStream = new MemoryInputSynapse();

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
        nnet.addLayer(hidden2, NeuralNet.HIDDEN_LAYER);
        nnet.addLayer(output, NeuralNet.OUTPUT_LAYER);
        nnet.setTeacher(trainer);
        this.monitor = nnet.getMonitor();
        monitor.setTrainingPatterns(INPUT.length);    // # of rows (patterns) contained in the input file
        monitor.setTotCicles(epoch);    // How many times the net must be trained on the input patterns
        monitor.setLearningRate(0.7);
        monitor.setMomentum(0.3);
        monitor.setLearning(true);    // The net must be trained
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

    public void test(double [][] inputArray) {
        // The input array used for this example
        if (nnet != null) {
            Layer input = nnet.getInputLayer();
            input.removeAllInputs();
            MemoryInputSynapse memInp = new MemoryInputSynapse();
            memInp.setAdvancedColumnSelector("1-24");
            input.addInputSynapse(memInp);
            memInp.setInputArray(inputArray);
            Layer output = nnet.getOutputLayer();
            output.removeAllOutputs();
            MemoryOutputSynapse memOut = new MemoryOutputSynapse();
            output.addOutputSynapse(memOut);
            nnet.getMonitor().setTotCicles(1);
            nnet.getMonitor().setTrainingPatterns(inputArray.length);
            nnet.getMonitor().setLearning(false);
            this.run();
            for (int i=0; i < inputArray.length; i++) {
                // Read the next pattern and print out it
                double[] pattern = memOut.getNextPattern();
                System.out.println("Output Pattern #"+(i+1)+" = "+dToString(pattern));
            }
            nnet.stop();
            System.out.println("Finished");
        }
    }

    private String dToString(double[] array){
        String output = "";
        for(double num:array){
            output += num+",";
        }
        return output.substring(0,output.length()-1);
    }

    @Override
    public void cicleTerminated(NeuralNetEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void errorChanged(NeuralNetEvent e) {
        Monitor mon = (Monitor) e.getSource();
        if (mon.getCurrentCicle() % 10 == 0)
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

    public void dataReader(String directory) throws IOException {
        File file = new File(directory);
        FileReader fReader = new FileReader(file);
        CSVReader csvReader = new CSVReader(fReader);
        List<String[]> sList = csvReader.readAll();
        LinkedList<double[]> inputs = new LinkedList<>(), labels = new LinkedList<>();
        for (String[] ss : sList) {
            double[] lTemp = new double[labelIndexM], iTemp = new double[ss.length - labelIndexM];
            for (int i = 0; i < ss.length; i++) {
                if (i < labelIndexM) lTemp[i] = Double.parseDouble(ss[i]);
                else iTemp[i - labelIndexM] = Double.parseDouble(ss[i]);
            }
            inputs.add(iTemp);
            labels.add(lTemp);
        }
        INPUT = new double[inputs.size()][inputs.get(0).length];
        LABEL = new double[labels.size()][labels.get(0).length];
        for (int i = 0; i < inputs.size(); i++) {
            INPUT[i] = inputs.get(i);
            LABEL[i] = labels.get(i);
        }
    }

    public void saveNeuralNet(String fileName) throws IOException {
        FileOutputStream stream = new FileOutputStream(fileName);
        ObjectOutputStream out = new ObjectOutputStream(stream);
        out.writeObject(nnet);
        out.close();
    }

    public void restoreNeuralNet(String fileName) throws IOException, ClassNotFoundException {
        NeuralNetLoader loader = new NeuralNetLoader(fileName);
        nnet = loader.getNeuralNet();
        nnet.getMonitor().addNeuralNetListener(this);
        this.run();
    }

    public void normalization(double[] array){

    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        NeuralNetwork nnetk = new NeuralNetwork();
        nnetk.run();
        nnetk.test(new double[][]{{-5.48568662497,0.368072423242,-1.15305957342,-2.2200270851,-3.99950165719,-2.6872426879,-2.67215503655,-2.43634687987,-3.30022162401,-0.382701016846,2.69671829985,-0.549114942655,-0.111772639531,0.240353709496,2.66967515741,-0.0544246411023,0.0375075389298,0.292966535875,2.82034452305,-0.177125021867,1.50550098949,5.27449901051,1.3407996673,-0.0224406887729}});
       // nnetk.saveNeuralNet("/Users/sunjh1999/IdeaProjects/SocialForceSimulation/resource/trainset4.net");
       // nnetk.restoreNeuralNet("/Users/sunjh1999/IdeaProjects/SocialForceSimulation/resource/trainset4.net");

    }
}
