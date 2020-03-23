package ru.mrhellko.gravity2d.engine;

public class ZoomSettings {
    private double cellX = 149_597_868_000.0;
    private double cellY = 149_597_868_000.0;
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
}
