package ru.mrhellko.gravity2d.entity;

import ru.mrhellko.gravity2d.engine.Viewport;

import java.awt.*;

public class BodyTrace extends AbstractBody {

    private Color color;
    public static final int viewR = 4;
    public static final int MIN_TRACE_DISTANCE_PX_SQR = 5 * 5;

    public BodyTrace(double x, double y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void render(Viewport viewport, Graphics graphics, BodyTrace tracePrev) {
        graphics.setColor(color);
        int xRender = viewport.getScreenX(x);
        int yRender = viewport.getScreenY(y);
        graphics.fillOval(xRender - viewR/2, yRender - viewR/2, viewR, viewR);

        if (tracePrev != null) {
            int xRenderPrev = viewport.getScreenX(tracePrev.getX());
            int yRenderPrev = viewport.getScreenY(tracePrev.getY());
            graphics.drawLine(xRender, yRender, xRenderPrev, yRenderPrev);
        }
    }

}
