package org.socialforce.app.gui;

import org.socialforce.drawer.impl.SceneDrawer;
import org.socialforce.geom.*;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.scene.Scene;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

/**
 * Created by Ledenel on 2016/10/23.
 */
public class SceneBoard extends JPanel/* implements Scrollable*/ {


    private final MouseWheelMoved mouseWheelMoved;
    private DragMouseAdapter mouseAdapter;

    public ResizeListener getResizeListener() {
        return resizeListener;
    }

    private ResizeListener resizeListener;

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
        SceneDrawer drawer = (SceneDrawer) scene.getDrawer();
        syncToDrawer(drawer);
        drawer.setScaleRate(drawer.calcScaleRate(scene.getBounds()));
        this.removeComponentListener(resizeListener);
        this.addComponentListener(resizeListener);
        this.removeMouseWheelListener(mouseWheelMoved);
        this.addMouseWheelListener(mouseWheelMoved);

        this.removeMouseListener(mouseAdapter);
        this.removeMouseMotionListener(mouseAdapter);

        this.addMouseListener(mouseAdapter);
        this.addMouseMotionListener(mouseAdapter);
    }

    /**
     * Creates a new <code>JPanel</code> with a double buffer
     * and a flow layout.
     */
    public SceneBoard() {
        //this.setPreferredSize(new Dimension(800,800));
        resizeListener = new ResizeListener();
        this.setBackground(Color.white);
        mouseAdapter = new DragMouseAdapter();
        mouseWheelMoved = new MouseWheelMoved();
    }

    Scene scene;

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    BufferedImage image;

    public void refresh(Graphics g) {
        if (image != null) {
            //synchronized (image) {
                g.drawImage(image, 0, 0, null);
            //}
        }
    }

    /**
     * Calls the UI delegate's paint method, if the UI delegate
     * is non-<code>null</code>.  We pass the delegate a copy of the
     * <code>Graphics</code> object to protect the rest of the
     * paint code from irrevocable changes
     * (for example, <code>Graphics.translate</code>).
     * <p>
     * If you override this in a subclass you should not make permanent
     * changes to the passed in <code>Graphics</code>. For example, you
     * should not alter the clip <code>Rectangle</code> or modify the
     * transform. If you need to do these operations you may find it
     * easier to create a new <code>Graphics</code> from the passed in
     * <code>Graphics</code> and manipulate it. Further, if you do not
     * invoker super's implementation you must honor the opaque property,
     * that is
     * if this component is opaque, you must completely fill in the background
     * in a non-opaque color. If you do not honor the opaque property you
     * will likely see visual artifacts.
     * <p>
     * The passed in <code>Graphics</code> object might
     * have a transform other than the identify transform
     * installed on it.  In this case, you might get
     * unexpected results if you cumulatively apply
     * another transform.
     *
     * @param g the <code>Graphics</code> object to protect
     * @see #paint
     * @see ComponentUI
     */



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        refresh(g);
    }

    /**
     * Returns the preferred size of the viewport for a view component.
     * For example, the preferred size of a <code>JList</code> component
     * is the size required to accommodate all of the cells in its list.
     * However, the value of <code>preferredScrollableViewportSize</code>
     * is the size required for <code>JList.getVisibleRowCount</code> rows.
     * A component without any properties that would selfAffect the viewport
     * size should just return <code>getPreferredSize</code> here.
     *
     * @return the preferredSize of a <code>JViewport</code> whose view
     * is this <code>Scrollable</code>
     * @see JViewport#getPreferredSize
     */
    //@Override
    public Dimension getPreferredScrollableViewportSize() {
        if(scene != null) {
            SceneDrawer sc = (SceneDrawer) scene.getDrawer();
            return getPreferred(scene.getBounds().getSize(),sc.getScaleRate());
        }
        return null;
    }

    /**
     * Components that display logical rows or columns should compute
     * the scroll increment that will completely expose one new row
     * or column, depending on the value of orientation.  Ideally,
     * components should handle a partially exposed row or column by
     * returning the distance required to completely expose the item.
     * <p>
     * Scrolling containers, like JScrollPane, will use this method
     * each time the user requests a unit scroll.
     *
     * @param visibleRect The view area visible within the viewport
     * @param orientation Either SwingConstants.VERTICAL or SwingConstants.HORIZONTAL.
     * @param direction   Less than zero to scroll up/left, greater than zero for down/right.
     * @return The "unit" increment for scrolling in the specified direction.
     * This value should always be positive.
     * @see JScrollBar#setUnitIncrement
     */
    //@Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 3;
    }

    /**
     * Components that display logical rows or columns should compute
     * the scroll increment that will completely expose one block
     * of rows or columns, depending on the value of orientation.
     * <p>
     * Scrolling containers, like JScrollPane, will use this method
     * each time the user requests a block scroll.
     *
     * @param visibleRect The view area visible within the viewport
     * @param orientation Either SwingConstants.VERTICAL or SwingConstants.HORIZONTAL.
     * @param direction   Less than zero to scroll up/left, greater than zero for down/right.
     * @return The "block" increment for scrolling in the specified direction.
     * This value should always be positive.
     * @see JScrollBar#setBlockIncrement
     */

    //@Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 3;
    }

    /**
     * Return true if a viewport should always force the width of this
     * <code>Scrollable</code> to match the width of the viewport.
     * For example a normal
     * text view that supported line wrapping would return true here, since it
     * would be undesirable for wrapped lines to disappear beyond the right
     * edge of the viewport.  Note that returning true for a Scrollable
     * whose ancestor is a JScrollPane effectively disables horizontal
     * scrolling.
     * <p>
     * Scrolling containers, like JViewport, will use this method each
     * time they are validated.
     *
     * @return True if a viewport should force the Scrollables width to match its own.
     */

    //@Override
    public boolean getScrollableTracksViewportWidth() {
        Dimension sz = getPreferredScrollableViewportSize();
        return sz == null || sz.getHeight() < this.getSize().height && sz.getWidth() < this.getSize().width;
    }

    /**
     * Return true if a viewport should always force the height of this
     * Scrollable to match the height of the viewport.  For example a
     * columnar text view that flowed text in left to right columns
     * could effectively disable vertical scrolling by returning
     * true here.
     * <p>
     * Scrolling containers, like JViewport, will use this method each
     * time they are validated.
     *
     * @return True if a viewport should force the Scrollables height to match its own.
     */
    //@Override
    public boolean getScrollableTracksViewportHeight() {
        Dimension sz = getPreferredScrollableViewportSize();
        return sz == null || sz.getHeight() < this.getSize().height && sz.getWidth() < this.getSize().width;
    }

    private class MouseWheelMoved extends MouseAdapter {
        /**
         * {@inheritDoc}
         *
         * @param e
         * @since 1.6
         */
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            super.mouseWheelMoved(e);
            int scaleCount = -e.getWheelRotation();

            SceneDrawer sc = (SceneDrawer) scene.getDrawer();
            double[] origin = new double[]{e.getX(),e.getY()};
            double[] before = sc.screenToScene(origin);
            synchronized (sc) {
                //sc.setCtrlHeight(SceneBoard.this.getHeight());
                //sc.setCtrlWidth(SceneBoard.this.getWidth());
                sc.setScaleRate(sc.getScaleRate() * Math.pow(1.1,scaleCount));
            }
            before = sc.sceneToScreen(before);
            double[] after = origin;
            after[0] -= before[0];
            after[1] -= before[1];
            //after = sc.sceneToScreen(after);
            sc.setOffsetX(sc.getOffsetX() + after[0]);
            sc.setOffsetY(sc.getOffsetY() + after[1]);
//            boardPack(scene.getBounds().getSize(),sc.getScaleRate());
           /* SceneBoard sct = SceneBoard.this;
            sct.getParent().invalidate();
            sct.setPreferredSize(getPreferred(scene.getBounds().getSize(),sc.getScaleRate()));
            sct.getParent().validate();*/
            syncToDrawer(sc);
        }
    }

    public class ResizeListener extends ComponentAdapter {
        /**
         * Invoked when the component's size changes.
         *
         * @param e
         */
        @Override
        public void componentResized(ComponentEvent e) {
            SceneDrawer sc = (SceneDrawer) scene.getDrawer();
            /*Vector size = scene.getBounds().getSize();
            double scaleRate = sc.getScaleRate();
            //boardPack(size, scaleRate);
            SceneBoard.this.repaint();
            SceneBoard.this.getParent().doLayout();
            SceneBoard.this.validate();

*/
            syncToDrawer(sc);
        }
    }

    protected void syncToDrawer(SceneDrawer sc) {
        synchronized (sc) {
            sc.setCtrlHeight(SceneBoard.this.getHeight());
            sc.setCtrlWidth(SceneBoard.this.getWidth());
        }
    }


    public Dimension getPreferred(Vector size, double scaleRate) {
        double minsc [] = new double[2];
        size.get(minsc);
        minsc[0] *= scaleRate;
        minsc[1] *= scaleRate;
        double minW = minsc[0] + 20;
        double minH = minsc[1] + 20;
        Dimension preferredSize = this.getParent().getSize();
        if(preferredSize.width < minW || preferredSize.height < minH) {

            preferredSize = new Dimension((int) minW, (int) minH);

            //this.getParent().invalidate();
        }
        return preferredSize;
    }


    private class DragMouseAdapter extends MouseAdapter {
        boolean dragging = false;

        /**
         * {@inheritDoc}
         *
         * @param e
         */
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            SceneDrawer sc = (SceneDrawer) SceneBoard.this.scene.getDrawer();
            lastX = e.getX() - sc.getOffsetX();
            lastY = e.getY() - sc.getOffsetY();

            dragging = true;

        }



        /**
         * {@inheritDoc}
         *
         * @param e
         */
        @Override
        public void mouseReleased(MouseEvent e) {
            super.mouseReleased(e);
            dragging = false;
        }

        double lastX = -1;
        double lastY = -1;


        /**
         * {@inheritDoc}
         *
         * @param e
         * @since 1.6
         */
        @Override
        public void mouseDragged(MouseEvent e) {
            super.mouseDragged(e);
            SceneDrawer sc = (SceneDrawer) SceneBoard.this.scene.getDrawer();
            sc.setOffsetX(e.getX() - lastX);
            sc.setOffsetY(e.getY() - lastY);
        }
    }
}
