package ru.mrhellko.gravity2d.ui;

import ru.mrhellko.gravity2d.engine.Engine;
import ru.mrhellko.gravity2d.engine.Viewport;
import ru.mrhellko.gravity2d.entity.Body;
import ru.mrhellko.gravity2d.entity.BodyBuilder;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class Form extends JFrame {
    private static final int POS_X = 400;
    private static final int POS_Y = 200;
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 800;
    private static final int DELTA_T_MAX = 20;
    public static final int COUNTS_PER_FRAME_MAX = 45;
    public static final int COUNTS_PER_FRAME_MIN = 0;
    public static final int SLIDER_SCALE = 300;
    private Engine engine;
    private Viewport viewport;
    private JPanel bottomPanel;
    private JLabel timeLabel;
    private JLabel countPerFrameLabel;
    private JSlider speedSlider;
    private JSlider precisionSlider;
    public static final double A_E = 149_597_868_000.0;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Form::new);
    }

    private Form() {
        viewport = new Viewport();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WINDOW_WIDTH, WINDOW_HEIGHT);
        bottomPanel = new JPanel();
        timeLabel = new JLabel();
        countPerFrameLabel = new JLabel();
        speedSlider = new JSlider(COUNTS_PER_FRAME_MIN, SLIDER_SCALE * COUNTS_PER_FRAME_MAX);
        speedSlider.addChangeListener(e -> {
            double value = ((JSlider)e.getSource()).getValue() / 1.0 / SLIDER_SCALE;
            engine.setCountsPerFrame((int) value);
        });
        precisionSlider = new JSlider(1, DELTA_T_MAX, 1);
        precisionSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                engine.setDeltaT(1.0 / ((JSlider)e.getSource()).getValue());
            }
        });

        initApplication();
        SpaceCanvas canvas = new SpaceCanvas(this, viewport, engine);
        add(canvas, BorderLayout.CENTER);

        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        bottomPanel.add(timeLabel);
        bottomPanel.add(countPerFrameLabel);
        bottomPanel.add(speedSlider);
        bottomPanel.add(precisionSlider);
        add(bottomPanel, BorderLayout.SOUTH);
        setTitle("Planets");
        setVisible(true);
    }

    private void initApplication() {
        engine = new Engine();
        engine.addBody(BodyBuilder.get()
                .setX(0).setY(0)
                .setVx(0).setVy(-(5.97E24*29783+(29783-1023.0)*7.3477E22)/1.9885E30)
                .setM(1.9885E30).setTitle("Sun").setColor(new Color(255, 0, 0))
                .build()
        );
        engine.addBody(BodyBuilder.get()
                .setX(Form.A_E).setY(0)
                .setVx(0).setVy(29783)
                .setM(5.97E24).setTitle("Earth").setColor(new Color(58, 138, 53))
                .build()
        );
        engine.addBody(BodyBuilder.get()
                .setX(Form.A_E).setY(384_401_000.0)
                .setVx(-1023).setVy(29783)
                .setM(7.3477E22).setTitle("Moon").setColor(new Color(58, 123, 234))
                .build()
        );
        engine.setDeltaT(1.0 / precisionSlider.getValue());
        double value = speedSlider.getValue() / 1.0 / SLIDER_SCALE;
        engine.setCountsPerFrame((int) value);
        Thread thread = new Thread(engine);
        thread.start();
    }

    public void onDrawFrame(SpaceCanvas canvas, Viewport viewport, Graphics g) {
        render(canvas, viewport, g);
        double time = engine.getTime();
        timeLabel.setText(String.format("%6d years %03d days %02d hours %02d min %02d sec",
                (int)(time / 3600 / 24 / 365),
                (int)(time / 3600 / 24) % 365,
                (int)(time / 3600) % 24,
                (int)(time / 60) % 60,
                (int)(time) % 60
        ));
        countPerFrameLabel.setText(String.format(" Counts per frame: %20d", engine.getCountsPerFrame()));
    }

    private void render(SpaceCanvas canvas, Viewport viewport, Graphics g) {
        viewport.updateFollowMode();
        for (Body body : engine.getBodyList()) {
            body.render(canvas, viewport, g);
        }
    }
}
