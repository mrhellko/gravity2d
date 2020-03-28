package ru.mrhellko.gravity2d.ui;

import ru.mrhellko.gravity2d.engine.Engine;
import ru.mrhellko.gravity2d.engine.Viewport;
import ru.mrhellko.gravity2d.entity.Body;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class SpaceCanvas extends JPanel implements MouseWheelListener {
    private Form formController;
    private Viewport viewport;
    private Engine engine;
    private static final double zoomIndex = 1.1;

    SpaceCanvas(Form formController, Viewport viewport, Engine engine) {
        this.formController = formController;
        this.viewport = viewport;
        this.engine = engine;
        addMouseWheelListener(this);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2 && !e.isConsumed()) {
                    for(Body body : engine.getBodyList()) {
                        if(isInsideOval(e.getPoint(), body)) {
                            viewport.setFollowBody(body);
                            formController.getRightPanel().setFollowBody(body);
                            break;
                        }
                    }
                    e.consume();
                }
                if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1 && !e.isConsumed()) {
                    boolean isBody = false;
                    for(Body body : engine.getBodyList()) {
                        if(isInsideOval(e.getPoint(), body)) {
                            isBody = true;
                            formController.getRightPanel().setSelected(body);
                            break;
                        }
                    }
                    if(!isBody) {
                        formController.getRightPanel().clearSelection();
                    }
                    e.consume();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
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

    private boolean isInsideOval(Point point, Body center) {
        int dx = point.x - viewport.getScreenX(center.getX());
        int dy = point.y - viewport.getScreenY(center.getY());
        return (dx * dx + dy * dy <= center.getViewR() * center.getViewR());
    }
}
