package ru.mrhellko.gravity2d.ui;

import ru.mrhellko.gravity2d.engine.ZoomSettings;

import javax.swing.*;
import java.awt.*;

public class SpaceCanvas extends JPanel {
    private Form formController;
    private ZoomSettings zoomSettings;

    SpaceCanvas(Form formController, ZoomSettings zoomSettings) {
        this.formController = formController;
        this.zoomSettings = zoomSettings;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.translate(getWidth() / 2, getHeight() / 2);
        formController.onDrawFrame(this, zoomSettings, g);
        try {
            Thread.sleep(16);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        repaint();
    }
}
