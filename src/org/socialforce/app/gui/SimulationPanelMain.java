package org.socialforce.app.gui;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.socialforce.app.ApplicationListener;
import org.socialforce.app.Applications.ApplicationLoader;
import org.socialforce.app.SocialForceApplication;
import org.socialforce.app.gui.util.ApplicationDisplayer;
import org.socialforce.model.Agent;
import org.socialforce.scene.Scene;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.stream.StreamSupport;

/**
 * Created by Ledenel on 2016/8/23.
 */
public class SimulationPanelMain implements ApplicationListener {
    private boolean paused = false;

    public SimulationPanelMain() {
        loader = new ApplicationLoader(this);
        refreshName();
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //super.mouseClicked(e);
                String s = timePerStepTextField.getText();
                //application.getModel().setTimePerStep(Double.valueOf(s));
                SwingWorker<Void, SocialForceApplication> worker = new SwingWorker<Void, SocialForceApplication>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        loader.current().start();
                        return null;
                    }
                };
                worker.execute();
                runButton.setEnabled(false);
                loadButton.setEnabled(false);
                skipButton.setEnabled(true);
                pauseButton.setEnabled(true);
                //loader.current().start();

            }
        });
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] objects = loader.stream().map(app -> new ApplicationDisplayer(app)).toArray();
                ApplicationDisplayer result = (ApplicationDisplayer) JOptionPane.showInputDialog(SimulationPanelMain.this.root,
                        "select a preset:", "Please select an applicaiton", JOptionPane.INFORMATION_MESSAGE, null,
                        objects, objects[0]);
                loader.select(result.application);
                refreshName();
            }
        });
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(paused) {
                    pauseButton.setText("Pause");
                    loader.current().resume();
                } else {
                    pauseButton.setText("Resume");
                    loader.current().pause();
                }
                paused = !paused;
            }
        });
        skipButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: 2017/3/21 在这里增加skip按钮点击的处理逻辑。
                loader.current().skip();
            }
        });
    }

    protected void refreshName() {
        currentScnenTextField.setText(loader.current().getName());
    }

    public static void main(String[] args) {

        try {
            BeautyEyeLNFHelper.launchBeautyEyeLNF();
        } catch (Exception e) {
        }
        try {
            JFrame frame = new JFrame("SimulationPanelMain");
            SimulationPanelMain mainPanel = new SimulationPanelMain();
            /*SocialForceApplication application = new ApplicationForDoorTest();//应用在这里！
            application.setApplicationListener(mainPanel);*/
            //mainPanel.setLoader(new ApplicationLoader(mainPanel));
            frame.setContentPane(mainPanel.root);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //frame.setResizable(false);
            frame.pack();
            frame.setVisible(true);

            //application.start();
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
    private JButton skipButton;
    private JPanel sceneContainer;
    private JPanel scene1;
    private JPanel scene2;
    private JPanel scene3;
    private JPanel scene4;
    private JLabel timeUsedLabel;
    private JTextArea logTextArea;
    private JTextField timePerStepTextField;
    private JLabel fpsLabel;
    private JButton loadButton;

    public ApplicationLoader getLoader() {
        return loader;
    }

    public void setLoader(ApplicationLoader loader) {
        this.loader = loader;
    }

    private ApplicationLoader loader;

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
        Scene def = StreamSupport.stream(application.getAllScenes().spliterator(), false)
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
        if (scene.getDrawer() == null) {
            shower1.setScene(scene);
        }
    }
}
