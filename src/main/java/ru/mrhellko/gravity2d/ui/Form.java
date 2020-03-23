package ru.mrhellko.gravity2d.ui;

import ru.mrhellko.gravity2d.engine.Engine;
import ru.mrhellko.gravity2d.engine.ZoomSettings;
import ru.mrhellko.gravity2d.entity.Body;

import javax.swing.*;
import java.awt.*;

public class Form extends JFrame {
    private static final int POS_X = 400;
    private static final int POS_Y = 200;
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 800;
    private Engine engine;
    private ZoomSettings zoomSettings;
    private JLabel timeLabel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Form::new);
    }

    private Form() {
        zoomSettings = new ZoomSettings();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WINDOW_WIDTH, WINDOW_HEIGHT);
        initApplication();
        SpaceCanvas canvas = new SpaceCanvas(this, zoomSettings);
        add(canvas, BorderLayout.CENTER);
        timeLabel = new JLabel();
        add(timeLabel, BorderLayout.AFTER_LAST_LINE);
        setTitle("Planets");
        setVisible(true);
    }

    private void initApplication() {
        engine = new Engine();
        engine.addBody(new Body(0, 0, 0, 0, 1.9885E30, "Sun", new Color(255, 0, 0)));
        engine.addBody(new Body(149_597_868_000.0, 0, 0, 29783*1.2, 5.97E24, "Earth", new Color(58, 138, 53)));
        engine.setDeltaT(1);
        Thread thread = new Thread(engine);
        thread.start();
    }

    public void onDrawFrame(SpaceCanvas canvas, ZoomSettings zoomSettings, Graphics g) {
        render(canvas, zoomSettings, g);
        timeLabel.setText(engine.getTime() / 3600.0 / 24.0 + " days");
    }

    private void render(SpaceCanvas canvas, ZoomSettings zoomSettings, Graphics g) {
        for (Body body : engine.getBodyList()) {
            body.render(canvas, zoomSettings, g);
        }
    }
}
