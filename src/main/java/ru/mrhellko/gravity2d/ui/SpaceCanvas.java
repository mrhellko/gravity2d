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
        for (int i = - getWidth() / 2; i < getWidth() / 2; i += getWidth() / 10) {
            g.drawLine(i, - getHeight() / 2, i, getHeight());
        }
        for (int i = - getHeight() / 2; i < getHeight() / 2; i += getHeight() / 8) {
            g.drawLine(- getWidth() / 2, i, getWidth(), i);
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int xPointLocationZoom = e.getX();
        int yPointLocationZoom = e.getY();
        viewport.updateZoom(e.getWheelRotation(), zoomIndex, xPointLocationZoom, yPointLocationZoom);
    }

}
