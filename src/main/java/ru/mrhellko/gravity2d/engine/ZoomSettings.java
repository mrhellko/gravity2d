package ru.mrhellko.gravity2d.engine;

public class ZoomSettings {
    private double cellX = 149_597_868_000.0;
    private double cellY = 149_597_868_000.0;
    private int scaleX = 100;
    private int scaleY = 100;

    public int getScaleX() {
        return scaleX;
    }

    public void setScaleX(int scaleX) {
        this.scaleX = scaleX;
    }

    public int getScaleY() {
        return scaleY;
    }

    public void setScaleY(int scaleY) {
        this.scaleY = scaleY;
    }

    public int getX(double x) {
        return (int) (x * scaleX / cellX);
    }

    public int getY(double y) {
        return (int) (y * scaleY / cellY);
    }
}
