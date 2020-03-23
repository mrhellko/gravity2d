package ru.mrhellko.gravity2d.ui;

import ru.mrhellko.gravity2d.engine.Engine;
import ru.mrhellko.gravity2d.engine.ZoomSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class SpaceCanvas extends JPanel implements MouseWheelListener {
    private Form formController;
    private ZoomSettings zoomSettings;
    Engine engine;

    SpaceCanvas(Form formController, ZoomSettings zoomSettings, Engine engine) {
        this.formController = formController;
        this.zoomSettings = zoomSettings;
        this.engine = engine;
        addMouseWheelListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.translate(getWidth() / 2, getHeight() / 2);
        paintGrid(g);
        formController.onDrawFrame(this, zoomSettings, g);
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
        if (e.getWheelRotation() < 0) {
            zoomSettings.setScaleX(zoomSettings.getScaleX() * 2);
            zoomSettings.setScaleY(zoomSettings.getScaleY() * 2);
        } else {
            zoomSettings.setScaleX(zoomSettings.getScaleX() / 2);
            zoomSettings.setScaleY(zoomSettings.getScaleY() / 2);
        }
    }
}
