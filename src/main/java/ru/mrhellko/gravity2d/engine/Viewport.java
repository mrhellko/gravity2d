package ru.mrhellko.gravity2d.engine;

import ru.mrhellko.gravity2d.ui.Form;

public class Viewport {
    private double cellX = Form.A_E;
    private double cellY = Form.A_E;

    private double left = -cellX * 6;
    private double right = cellX * 6;
    private double top = - cellY * 4;
    private double down = cellY * 4;
    private int width = 1200;
    private int height = 800;

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
    }
}
