package ru.mrhellko.gravity2d.entity;

public abstract class AbstractBody {
    protected double x;
    protected double y;


    public double distanceFrom(Body other) {
        double otherX = other.getX();
        double otherY = other.getY();
        return Math.sqrt((x - otherX) * (x - otherX) + (y - otherY) * (y - otherY));
    }

    public double squareDistanceFrom(Body other) {
        double thisX = this.getX();
        double thisY = this.getY();
        double otherX = other.getX();
        double otherY = other.getY();
        return (thisX - otherX) * (thisX - otherX) + (thisY - otherY) * (thisY - otherY);
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

}
