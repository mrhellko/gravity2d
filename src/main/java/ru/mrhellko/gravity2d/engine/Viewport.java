package ru.mrhellko.gravity2d.engine;

import ru.mrhellko.gravity2d.entity.Body;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Viewport {
    private double cellX = 149_597_868_000.0;
    private double cellY = 149_597_868_000.0;

    private double left = -cellX * 5;
    private double right = cellX * 5;
    private double top = - cellY * 4;
    private double down = cellY * 4;
    private int width = 1200;
    private int height = 800;
    private double zoomFactor = 100;

    public double getZoomFactor() {
        return zoomFactor;
    }

    public void setZoomFactor(double zoomFactor) {
        this.zoomFactor = zoomFactor;
    }

    public int getX(double x) {
        return (int) (x * zoomFactor / cellX);
    }

    public int getY(double y) {
        return (int) (y * zoomFactor / cellY);
    }

    public Point getScreenBodyCoordinates(Body body) {
        if (body.getX() <= right && body.getX() >= left &&
                body.getY() <= top && body.getY() >= down) {
            return new Point((int) (body.getX() / (right - left) * width), (int) (body.getY() / (down - top) * height));
        }
        return null;
    }

    public double getRealX(int x) {
        return (right - left) * x / width;
    }

    public double getRealY(int y) {
        return (down - top) * y / height;
    }

    public int getScreenX(double x) {
        return (int) (x / (right - left) * width);
    }

    public int getScreenY(double y) {
        return (int) (y / (down - top) * height);
    }

    public void zoomIn(int x, int y) {

    }

}
