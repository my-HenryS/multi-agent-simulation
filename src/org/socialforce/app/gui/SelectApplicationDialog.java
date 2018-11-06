package org.socialforce.app.gui;

import org.socialforce.app.Applications.ApplicationLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SelectApplicationDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JList list1;

    public SelectApplicationDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        SelectApplicationDialog dialog = new SelectApplicationDialog(new ApplicationLoader(null));
        dialog.pack();
        dialog.setVisible(true);

    }

    /**
     * Creates a modeless dialog without a title and without a specified
     * {@code Frame} owner.  A shared, hidden frame will be
     * set as the owner of the dialog.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by {@code JComponent.getDefaultLocale}.
     * <p>
     * NOTE: This constructor does not allow you to create an unowned
     * {@code JDialog}. To create an unowned {@code JDialog}
     * you must use either the {@code JDialog(Window)} or
     * {@code JDialog(Dialog)} constructor with an argument of
     * {@code null}.
     *
     * @throws HeadlessException if {@code GraphicsEnvironment.isHeadless()}
     *                           returns {@code true}.
     * @see GraphicsEnvironment#isHeadless
     * @see JComponent#getDefaultLocale
     */
    public SelectApplicationDialog(ApplicationLoader loader) {
        this.loader = loader;
    }

    ApplicationLoader loader;



    private void createUIComponents() {
        // this.list1 = new JList(loader.stream().map(app -> new ApplicationDisplayer(app)).toArray());
        // TODO: place custom component creation code here

    }
}
