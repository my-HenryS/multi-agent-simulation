package org.socialforce.app.gui;

import org.socialforce.app.ApplicationListener;
import org.socialforce.app.Applications.ApplicationForECTest;
import org.socialforce.app.Scene;
import org.socialforce.app.Applications.SimpleApplication;
import org.socialforce.app.SocialForceApplication;
import org.socialforce.model.Agent;

import javax.swing.*;
import java.awt.*;
import java.util.stream.StreamSupport;

/**
 * Created by Ledenel on 2016/8/23.
 */
public class SimulationPanelMain implements ApplicationListener {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException e) {
            //  e.printStackTrace();
        } catch (InstantiationException e) {
            //  e.printStackTrace();
        } catch (IllegalAccessException e) {
            //  e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            //  e.printStackTrace();
        } finally {
            //UIManager.look
        }
        try {
            JFrame frame = new JFrame("SimulationPanelMain");
            SimulationPanelMain mainPanel = new SimulationPanelMain();
            ApplicationForECTest application = new ApplicationForECTest();//应用在这里！
            //SimpleApplication application = new SimpleApplication();
            application.setApplicationListener(mainPanel);
            frame.setContentPane(mainPanel.root);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.pack();
            frame.setVisible(true);
            mainPanel.setApplication(application);
            application.start();
        } catch (HeadlessException e) {
            System.out.println("GUI Not Supported on this machine.");
        }

    }

    private JPanel root;
    private JTextField currentScnenTextField;
    private JButton importFromFileButton;
    private JComboBox agentPathFindingComboBox;
    private JButton runButton;
    private JButton pauseButton;
    private JButton stopButton;
    private JPanel sceneContainer;
    private JPanel scene1;
    private JPanel scene2;
    private JPanel scene3;
    private JPanel scene4;
    private JLabel timeUsedLabel;
    private JTextArea logTextArea;
    private JTextField timePerStepTextField;
    private JLabel fpsLabel;

    SceneShower shower1;

    private void createUIComponents() {
        // TODO: place custom component creation code here
        shower1 = new SceneShower("Scene 1");
        scene1 = shower1.getRoot();
        scene2 = new SceneShower("Scene 2").getRoot();
        scene3 = new SceneShower("Scene 3").getRoot();
        scene4 = new SceneShower("Scene 4").getRoot();
    }

    public SocialForceApplication getApplication() {
        return application;
    }

    public void setApplication(SocialForceApplication application) {
        this.application = application;

        // TODO: here, panel only find a default scene and show it.
        Scene def = StreamSupport.stream(application.getAllScenes().spliterator(),false)
                .findFirst()
                .orElse(null);
        shower1.setScene(def);
    }

    SocialForceApplication application;

    /**
     * triggered while a agent is escaped.
     *
     * @param scene       the scene where the agent in.
     * @param escapeAgent the escaped agent.
     */
    @Override
    public void onAgentEscape(Scene scene, Agent escapeAgent) {

    }

    /**
     * triggered while the application is start.
     */
    @Override
    public void onStart() {

    }

    /**
     * triggered while the application is stop.
     *
     * @param terminated whether the application is forced to shut down.
     */
    @Override
    public void onStop(boolean terminated) {

    }

    /**
     * triggered while a scene is step-forwarded.
     *
     * @param scene the scene steped-forwarded.
     */
    @Override
    public void onStep(Scene scene) {
        scene.getDrawer().draw(scene);
        this.shower1.getBoard().refresh();
    }
}
