package org.socialforce.app.gui;

import org.socialforce.app.Application;
import org.socialforce.app.ApplicationListener;
import org.socialforce.app.Applications.ApplicationForCanteen;
import org.socialforce.app.Applications.ApplicationForCrossFlow;
import org.socialforce.app.Applications.ApplicationForDoorTest;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.model.Agent;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.impl.Wall;
import org.socialforce.scene.Scene;
import org.socialforce.scene.impl.SimpleScene;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditModeBoard {
    private JPanel rootPanel;
    private JPanel sceneBoardPanel;
    private JButton startButton;
    private SceneShower sceneShower;

    public EditModeBoard() {
        Scene scene = new SimpleScene(new Box2D(-50,-50,100,100));


        Wall wall = new Wall(new Box2D(0, 0, 2.2, 5.5));
        scene.addEntity(wall);
        InteractiveEntity entity = scene.getStaticEntities().selectTop(new Point2D(1.2,1.4));
        entity.getPhysicalEntity().moveTo(new Point2D(4.4, 7.7));
        //scene.removeEntity(entity);

       startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ApplicationForCanteen canteen = new ApplicationForCanteen();
                canteen.setApplicationListener(new ApplicationListener() {
                    private boolean first = true;

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onStop(boolean terminated) {

                    }

                    @Override
                    public void onStep(Application application) {
                        if(first) {
                            sceneShower.setScene(application.getCurrentScene());
                            first = false;
                        }
                    }

                    @Override
                    public void onAgentEscape(Scene scene, Agent escapeAgent) {

                    }
                });
                SwingWorker<Void, Void> exec = new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        canteen.start();
                        return null;
                    }
                };

                exec.execute();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("EditModeBoard");
        frame.setContentPane(new EditModeBoard().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        sceneShower = new SceneShower("testor");
        sceneBoardPanel = sceneShower.getRoot();

    }
}
