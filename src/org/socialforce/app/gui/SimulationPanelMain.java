package org.socialforce.app.gui;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.socialforce.app.Application;
import org.socialforce.app.ApplicationListener;
import org.socialforce.app.Applications.ApplicationLoader;
import org.socialforce.app.gui.util.ApplicationDisplayer;
import org.socialforce.drawer.impl.SceneDrawer;
import org.socialforce.model.Agent;
import org.socialforce.scene.Scene;
import org.socialforce.settings.Settings;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.stream.StreamSupport;

/**
 * Created by Ledenel on 2016/8/23.
 */
public class SimulationPanelMain implements ApplicationListener {
    private boolean paused = false;
    private boolean running = false;
    int maxDelay = 40;
    public static JFrame frame;



    protected SimulationPanelMain() {
        if(loader != null){
            refreshName();
        }
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //super.mouseClicked(e);
                String s = timePerStepTextField.getText();
                //application.getModel().setTimePerStep(Double.valueOf(s));
                SwingWorker<Void, Application> worker = new SwingWorker<Void, Application>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        try {
                            loader.current().start();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        //场景运行完毕后
                        running = false;
                        runButton.setText("Run");
                        loadButton.setEnabled(true);
                        skipButton.setEnabled(false);
                        pauseButton.setEnabled(false);
                        timePerStepTextField.setEnabled(true);
                        agentPathFindingComboBox.setEnabled(true);
                        return null;
                    }
                };
                if(running == false){
                    running = true;
                    worker.execute();
                    runButton.setText("Terminate");
                    loadButton.setEnabled(false);
                    timePerStepTextField.setEnabled(false);
                    agentPathFindingComboBox.setEnabled(false);
                    skipButton.setEnabled(true);
                    pauseButton.setEnabled(true);
                    /*设定延时*/
 //=  (int)(maxDelay * (1 - (double)slider1.getValue() / 100));
                    int delay = getMaxDelay();
                    loader.current().setMinStepForward(delay);
                }

                else{
                    running = false;
                    if(paused) {
                        pauseButton.setText("Pause");
                        loader.current().resume();
                        paused = !paused;
                    }
                    loader.current().terminate();
                    runButton.setText("Run");
                    loadButton.setEnabled(true);
                    skipButton.setEnabled(false);
                    pauseButton.setEnabled(false);
                    timePerStepTextField.setEnabled(true);
                    agentPathFindingComboBox.setEnabled(true);
                }


            }
        });
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] objects = loader.stream().map(app -> new ApplicationDisplayer(app)).toArray();
                ApplicationDisplayer result = (ApplicationDisplayer) JOptionPane.showInputDialog(SimulationPanelMain.this.root,
                        "select a preset:", "Please select an applicaiton", JOptionPane.INFORMATION_MESSAGE, null,
                        objects, objects[0]);
                if(result != null)
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
                if(paused) {
                    pauseButton.setText("Pause");
                    loader.current().resume();
                    paused = !paused;
                }
                loader.current().skip();
            }
        });
        slider1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int delay = getMaxDelay();
                loader.current().setMinStepForward(delay);
            }
        });
        shower1.getBoard().setTextArea(logTextArea);
        loadButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String file = getFileFromDialog("选择配置文件", FileDialog.LOAD);
                if (file != null) {
                    fileName = file;
                    reloadSettings();
                }
            }
        });
        reloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reloadSettings();
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String saveTip = "保存配置文件";
                int mode = FileDialog.SAVE;
                String file = getFileFromDialog(saveTip, mode);
                if(file != null) {
                    fileName = file;
                    SimulationPanelMain.this.nowSettings.setText(fileName);
                    Settings.saveToJson(new File(fileName));
                }
            }
        });
    }

    private int getMaxDelay() {
        int framerate = slider1.getValue();
        double delayd = 1000.0 / (framerate+1e-6);
        int delay = (int)Math.round(delayd);
        return delay;
    }

    private String getFileFromDialog(String saveTip, int mode) {
        FileDialog dialog = new FileDialog(getFrame(), saveTip, mode);
        dialog.setModal(true);
        dialog.setMultipleMode(false);
        dialog.setVisible(true);

        return dialog.getFile();
    }

    private Frame getFrame() {
        return (Frame) SimulationPanelMain.this.root.getRootPane().getParent();
    }

    private void reloadSettings() {
        if(fileName != null) {
            this.nowSettings.setText(fileName);
            Settings.loadFromJson(new File(fileName));
        }else {
            JOptionPane.showMessageDialog(getFrame(), "没有可供加载的配置文件。");
        }
    }

    private String fileName;

    protected void refreshName() {
        currentScnenTextField.setText(loader.current().getName());
    }

    public static void main(String[] args) {
        try {
            BeautyEyeLNFHelper.launchBeautyEyeLNF();
            UIManager.put("RootPane.setupButtonVisible",false);
        } catch (Exception e) {
        }
        try {
            //JFrame frame = new JFrame("SimulationPanelMain");
            frame = new JFrame("Epimetheus");
            //SimulationPanelMain mainPanel = new SimulationPanelMain();
            ApplicationMain mainPanel = new ApplicationMain();
            /*Application application = new ApplicationForDoorTest();//应用在这里！
            application.setApplicationListener(mainPanel);*/
            //mainPanel.setLoader(new ApplicationLoader(mainPanel));
            frame.setContentPane(mainPanel.demoP);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //frame.setResizable(false);
            frame.pack();
            Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
            frame.setLocation((int)(size.getWidth()-frame.getWidth())/2,
                    (int)(size.getHeight()-frame.getHeight())/2);
            frame.setVisible(true);

            //application.start();
        } catch (HeadlessException e) {
            System.out.println("GUI Not Supported on this machine.");
        }

    }

    public JPanel root;//private
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
    private JTextArea logTextArea;
    private JTextField timePerStepTextField;
    private JButton loadButton;
    private JSlider slider1;
    private JPanel settings;
    private JButton loadButton1;
    private JLabel nowSettings;
    private JButton reloadButton;
    private JButton saveButton;

    public ApplicationLoader getLoader() {
        return loader;
    }

    public void setLoader(ApplicationLoader loader) {
        this.loader = loader;
        refreshName();
    }

    private ApplicationLoader loader;

    SceneShower shower1;

    private void createUIComponents() {
        // TODO: place custom component creation code here
        shower1 = new SceneShower("Scene 1");
        scene1 = shower1.getRoot();

    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;

        // TODO: here, panel only find a default scene and show it.
        Scene def = StreamSupport.stream(application.getAllScenes().spliterator(), false)
                .findFirst()
                .orElse(null);
        shower1.setScene(def);
    }

    Application application;

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
     * @param application the application steped-forwarded.
     */
    @Override
    public void onStep(Application application) {
        if (application.getCurrentScene().getDrawer() == null) {
            application.manageDrawer((SceneDrawer)shower1.getDrawerInstaller().getRegisteredDrawers().iterator().next());
            shower1.setScene(application.getCurrentScene());
        }
    }
}
