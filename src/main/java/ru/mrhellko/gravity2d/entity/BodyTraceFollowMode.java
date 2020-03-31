package ru.mrhellko.gravity2d.entity;

import ru.mrhellko.gravity2d.engine.Viewport;

import java.awt.*;

public class BodyTraceFollowMode extends BodyTrace{
    protected int xInt;
    protected int yInt;
    protected Color color;

    public BodyTraceFollowMode(int xInt, int yInt, Color color) {
        this.xInt = xInt;
        this.yInt = yInt;
        this.color = color;
    }

    public void updateZoom(int wheelRotation, double zoomIndex, int xPointLocationZoom, int yPointLocationZoom, Viewport viewport) {
        if (wheelRotation > 0) {
            zoomIndex = 1 / zoomIndex;
        }
        int centerX = viewport.getCenterScreenX();
        int centerY = viewport.getCenterScreenY();
        xInt = (int) (centerX + zoomIndex * (xInt - centerX));
        yInt = (int) (centerY + zoomIndex * (yInt - centerY));
    }

    public double squareDistanceScreenFrom(Body other, Viewport viewport) {
        double otherX =  viewport.getScreenX(other.getX());
        double otherY =  viewport.getScreenY(other.getY());
        return (xInt - otherX) * (xInt - otherX) + (yInt - otherY) * (yInt - otherY);
    }

    @Override
    public int getScreenX(Viewport viewport) {
        return xInt;
    }

    @Override
    public int getScreenY(Viewport viewport) {
        return yInt;
    }

}
