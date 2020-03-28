package ru.mrhellko.gravity2d.entity;

import java.awt.*;

public class BodyBuilder {
    public static final double MIN_TRACE_DISTANCE = 1_000_000 * 1000; //km
    private double x;
    private double y;
    private double vx;
    private double vy;
    private double m;
    private String title;
    private Color color;
    private int viewR;
    private double midDistanceTrace = MIN_TRACE_DISTANCE;

    public static BodyBuilder get() {
        return new BodyBuilder();
    }

    public BodyBuilder setX(double x) {
        this.x = x;
        return this;
    }

    public BodyBuilder setY(double y) {
        this.y = y;
        return this;
    }

    public BodyBuilder setVx(double vx) {
        this.vx = vx;
        return this;
    }

    public BodyBuilder setVy(double vy) {
        this.vy = vy;
        return this;
    }

    public BodyBuilder setM(double m) {
        this.m = m;
        return this;
    }

    public BodyBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public BodyBuilder setColor(Color color) {
        this.color = color;
        return this;
    }

    public BodyBuilder setViewR(int viewR) {
        this.viewR = viewR;
        return this;
    }

    public BodyBuilder setMidDistanceTrace(double midDistanceTrace) {
        this.midDistanceTrace = midDistanceTrace;
        return this;
    }

    public Body build() {
        if(viewR == 0) viewR = 20;
        if(color == null) color = new Color(0, 0, 0);
        if(title == null) title = "";
        if(m <= 0) throw new IllegalArgumentException();
        return new Body(x, y, vx, vy, m, title, color, viewR, midDistanceTrace);
    }
}
