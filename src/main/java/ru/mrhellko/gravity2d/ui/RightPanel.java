package ru.mrhellko.gravity2d.ui;

import ru.mrhellko.gravity2d.entity.Body;

import javax.swing.*;

public class RightPanel extends JPanel {
    private Body body = null;
    private Body prevBody = null;
    private JLabel jLabelHeader = new JLabel();
    private JLabel jLabelTitle = new JLabel();
    private JLabel jLabelX = new JLabel();
    private JLabel jLabelY = new JLabel();
    private JLabel jLabelVx = new JLabel();
    private JLabel jLabelVy = new JLabel();
    private JLabel jLabelV = new JLabel();
    private JLabel jLabelM = new JLabel();
    private JLabel jLabelDistanceTitle = new JLabel();
    private JLabel jLabelDistance = new JLabel();
    private boolean viewDistance = false;

    boolean isViewDistance() {
        return viewDistance;
    }

    RightPanel() {
        jLabelHeader.setText("Free View");
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(jLabelHeader);
        this.add(jLabelTitle);
        this.add(jLabelX);
        this.add(jLabelY);
        this.add(jLabelVx);
        this.add(jLabelVy);
        this.add(jLabelV);
        this.add(jLabelM);
        this.add(jLabelDistanceTitle);
        this.add(jLabelDistance);
    }

    void clearSelection() {
        this.body = null;
        this.prevBody = null;
        this.viewDistance = false;
        jLabelTitle.setText("");
        jLabelX.setText("");
        jLabelY.setText("");
        jLabelVx.setText("");
        jLabelVy.setText("");
        jLabelV.setText("");
        jLabelM.setText("");
        jLabelDistanceTitle.setText("");
        jLabelDistance.setText("");
    }

    void setSelected(Body body) {
        if(this.body == null && body != null) {
            this.body = body;
            this.viewDistance = false;
        } else {
            this.viewDistance = true;
            this.prevBody = this.body;
            this.body = body;
        }
    }
    Body getSelected() {
        return this.body;
    }

    void printSelectedBodyInfo() {
        jLabelTitle.setText(body.getTitle());
        jLabelX.setText(String.format("x: %1.4e", body.getX()));
        jLabelY.setText(String.format("y: %1.4e", body.getY()));
        jLabelVx.setText(String.format("Vx: %1.4e", body.getVx()));
        jLabelVy.setText(String.format("Vy: %1.4e", body.getVy()));
        jLabelV.setText(String.format("V: %1.4e", Math.sqrt(body.getVy()*body.getVy() + body.getVx()*body.getVx())));
        jLabelM.setText(String.format("M: %1.4e", body.getM()));
    }

    void printDistanceBodiesInfo() {
        jLabelDistanceTitle.setText(String.format("Distance from %s to %s:", prevBody.getTitle(), body.getTitle()));
        jLabelDistance.setText(String.format("d: %1.4e", prevBody.distanceFrom(body)));
    }

    void setFollowBody(Body body) {
        if(body != null) {
            jLabelHeader.setText("Follow to " + body.getTitle());
        } else {
            jLabelHeader.setText("Free View");
        }
    }
}
