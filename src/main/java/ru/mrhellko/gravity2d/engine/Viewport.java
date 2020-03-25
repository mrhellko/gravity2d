package ru.mrhellko.gravity2d.engine;

import ru.mrhellko.gravity2d.entity.Body;
import ru.mrhellko.gravity2d.ui.Form;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Viewport {
    private double cellX = Form.A_E;
    private double cellY = Form.A_E;

    private double left = -cellX * 5;
    private double right = cellX * 5;
    private double top = - cellY * 4;
    private double down = cellY * 4;
    private int width = 1200;
    private int height = 800;
    private double zoomFactor = 100;
    {
        System.out.println(String.format("left: %s, right: %s, top: %s, down: %s", left, right, top, down));

    }

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
        return left + (right - left) * x / width;
    }

    public double getRealY(int y) {
        return top + (down - top) * y / height;
    }

    public int getScreenX(double x) {
        return (int) ((x - left) / (right - left) * width);
    }

    public int getScreenY(double y) {
        return (int) ((y - top) / (down - top) * height);
    }

    public void zoomIn(int x, int y) {

    }

    public void updateZoom(int wheelRotation, double zoomIndex, int xPointLocationZoom, int yPointLocationZoom) {
        if (wheelRotation < 0) {
            zoomIndex = 1 / zoomIndex;
        }
        double newLeft = zoomIndex * this.left + (1 - zoomIndex) * getRealX(xPointLocationZoom);
        double newRight = zoomIndex * this.right + (1 - zoomIndex) * getRealX(xPointLocationZoom);
        double newTop = zoomIndex * this.top + (1 - zoomIndex) * getRealY(yPointLocationZoom);
        double newDown = zoomIndex * this.down + (1 - zoomIndex) * getRealY(yPointLocationZoom);

        this.left = newLeft;
        this.right = newRight;
        this.top = newTop;
        this.down = newDown;
        System.out.println(String.format("left: %s, right: %s, top: %s, down: %s", left, right, top, down));
    }
}
