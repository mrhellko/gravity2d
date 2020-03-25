package ru.mrhellko.gravity2d.ui;

import ru.mrhellko.gravity2d.engine.Engine;
import ru.mrhellko.gravity2d.engine.Viewport;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;


public class SpaceCanvas extends JPanel implements MouseWheelListener {
    private Form formController;
    private Viewport viewport;
    private Engine engine;
    public static final double zoomIndex = 1.1;

    SpaceCanvas(Form formController, Viewport viewport, Engine engine) {
        this.formController = formController;
        this.viewport = viewport;
        this.engine = engine;
        addMouseWheelListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintGrid(g);
        formController.onDrawFrame(this, viewport, g);
        engine.updateRealCounts();
        try {
            Thread.sleep(16);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        repaint();
    }

    private void paintGrid(Graphics g) {
        for (int i = 0; i < getWidth() ; i += getWidth() / 12) {
            g.drawLine(i, 0, i, getHeight());
        }
        for (int i = 0; i < getHeight() ; i += getHeight() / 8) {
            g.drawLine(0, i, getWidth(), i);
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        viewport.updateZoom(e.getWheelRotation(), zoomIndex, e.getX(), e.getY());
    }

}
