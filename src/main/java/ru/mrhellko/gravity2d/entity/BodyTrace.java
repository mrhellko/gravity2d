package ru.mrhellko.gravity2d.entity;

import ru.mrhellko.gravity2d.engine.Viewport;

import java.awt.*;

public class BodyTrace extends AbstractBody{

    protected Color color;

    public BodyTrace() {

    }

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
        int xRender = getScreenX(viewport);
        int yRender = getScreenY(viewport);
        graphics.fillOval(xRender - VIEW_R_BODY_TRACE /2, yRender - VIEW_R_BODY_TRACE /2, VIEW_R_BODY_TRACE, VIEW_R_BODY_TRACE);

        if (tracePrev != null) {
            int xRenderPrev = tracePrev.getScreenX(viewport);
            int yRenderPrev = tracePrev.getScreenY(viewport);
            graphics.drawLine(xRender, yRender, xRenderPrev, yRenderPrev);
        }
    }

    public int getScreenX(Viewport viewport) {
        return viewport.getScreenX(x);
    }

    public int getScreenY(Viewport viewport) {
        return viewport.getScreenY(y);
    }
}
