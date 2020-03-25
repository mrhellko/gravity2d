package ru.mrhellko.gravity2d.entity;

import ru.mrhellko.gravity2d.engine.Viewport;
import ru.mrhellko.gravity2d.ui.SpaceCanvas;

import java.awt.*;

public class Body {
    private double x;
    private double y;
    private double vx;
    private double vy;
    private double Fx;
    private double Fy;
    private double m;
    private String title;
    private Color color;
    public static int viewR = 20;

    public void render(SpaceCanvas canvas, Viewport viewport, Graphics graphics) {
        graphics.setColor(color);
        int xRender;
        int yRender;
        if (this == viewport.getFollowBody()) {
            xRender = viewport.getCenterScreenX();
            yRender = viewport.getCenterScreenY();
        } else {
            xRender = viewport.getScreenX(x);
            yRender = viewport.getScreenY(y);
        }
        graphics.fillOval(xRender - viewR/2, yRender - viewR/2, viewR, viewR);
    }

    public void setNewValues(double x, double y, double vx, double vy, double Fx, double Fy) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.Fx = Fx;
        this.Fy = Fy;
    }

    public double getFx() {
        return Fx;
    }

    public void setFx(double fx) {
        Fx = fx;
    }

    public double getFy() {
        return Fy;
    }

    public void setFy(double fy) {
        Fy = fy;
    }

    public double deltaX(Body other) {
        return other.x - x;
    }

    public double deltaY(Body other) {
        return other.y - y;
    }

    public double distanceFrom(Body other) {
        return Math.sqrt((x - other.x) * (x - other.x) + (y - other.y) * (y - other.y));
    }
    public double squareDistanceFrom(Body other) {
        return (x - other.x) * (x - other.x) + (y - other.y) * (y - other.y);
    }

    public Body(double x, double y, double vx, double vy, double m, String title, Color color) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.m = m;
        this.title = title;
        this.color = color;
    }

    public Body(double x, double y, double vx, double vy, double m) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.m = m;
        this.title = "";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getVx() {
        return vx;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public double getVy() {
        return vy;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public double getM() {
        return m;
    }

    public void setM(double m) {
        this.m = m;
    }
}
