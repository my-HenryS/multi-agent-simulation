package org.socialforce.app.gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;

/**
 * Created by Ledenel on 2016/8/23.
 */
public class SceneShower {
    private JCheckBox visibleCheckBox;
    private JPanel showPanel;
    private JPanel root;
    private String title;

    public SceneShower(String title) {
        this.title = title;
    }

    public JPanel getRoot() {
        root.setBorder(BorderFactory.createTitledBorder(title));
        return root;
    }

    private void createUIComponents() {
        root = new JPanel();
        //root.setBorder(BorderFactory.createTitledBorder(title));
        // TODO: place custom component creation code here
    }
}
