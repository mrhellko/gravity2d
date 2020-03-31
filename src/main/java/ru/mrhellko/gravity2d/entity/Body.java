package ru.mrhellko.gravity2d.entity;

import ru.mrhellko.gravity2d.engine.Viewport;
import ru.mrhellko.gravity2d.ui.SpaceCanvas;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Body extends AbstractBody {
    public static final int TRACE_MAX_SIZE = 200;
    private double vx;
    private double vy;
    private double Fx;
    private double Fy;
    private double m;
    private String title;
    private Color color;
    private int viewR;
    private double midDistanceTrace;
    private LinkedList<BodyTrace> tracesGlobalMode = new LinkedList<>();
    private LinkedList<BodyTraceFollowMode> tracesFollowMode = new LinkedList<>();
    private List<Color> colorIndex = new ArrayList<>();

    public Body(double x, double y, double vx, double vy, double m, String title, Color color, int viewR, double midDistanceTrace) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.m = m;
        this.title = title;
        this.color = color;
        this.viewR = viewR;
        this.midDistanceTrace = midDistanceTrace;
        tracesGlobalMode.add(new BodyTrace(x, y, color));
        createColorIndex();
    }

    public void render(SpaceCanvas canvas, Viewport viewport, Graphics graphics) {
        //Пересчитаем списки со следами
        processTrace(viewport);

        //Нарисуем само тело
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

        //отрисуем следы
        Body followBody = viewport.getFollowBody();
        if (followBody != this) {
            List<? extends BodyTrace> traces = followBody == null ? tracesGlobalMode : tracesFollowMode;
            for (int i = 0; i < traces.size(); i++) {
                BodyTrace tracePrev = null;
                if (i > 0) {
                    tracePrev = traces.get(i - 1);
                }
                BodyTrace trace = traces.get(i);
                trace.setColor(colorIndex.get(i * TRACE_MAX_SIZE / traces.size()));
                trace.render(viewport, graphics, tracePrev);
            }

            //соединим последний след с телом линией
            BodyTrace lastTrace = traces.get(traces.size() - 1);
            int xLastTraceRender = lastTrace.getScreenX(viewport);
            int yLastTraceRender = lastTrace.getScreenY(viewport);
            graphics.drawLine(xRender, yRender, xLastTraceRender, yLastTraceRender);
        }
    }

    private void processTrace(Viewport viewport) {
        if (checkAddTraceGlobalMode()) {
            tracesGlobalMode.add(new BodyTrace(x, y, color));
        }
        if (tracesGlobalMode.size() == TRACE_MAX_SIZE) {
            tracesGlobalMode.remove();
        }

        if(checkAddTraceFollowMode(viewport)) {
            tracesFollowMode.add(new BodyTraceFollowMode(viewport.getScreenX(x), viewport.getScreenY(y), color));
        }
        if (tracesFollowMode.size() == TRACE_MAX_SIZE) {
            tracesFollowMode.remove();
        }
    }

    private boolean checkAddTraceGlobalMode() {
        BodyTrace lastTrace = tracesGlobalMode.get(tracesGlobalMode.size()-1);
//        int dist = firstTrace.squareDistanceFromOnScreen(this, viewport);
        double realDist = lastTrace.distanceFrom(this);
        return realDist >= midDistanceTrace;
    }

    private boolean checkAddTraceFollowMode(Viewport viewport) {
        Body followBody = viewport.getFollowBody();
        if (followBody == null || followBody == this) return false;
        if (tracesFollowMode.size() == 0) return true;
        BodyTraceFollowMode lastTrace = tracesFollowMode.get(tracesFollowMode.size()-1);
        double screenDist = lastTrace.squareDistanceScreenFrom(this, viewport);
        return screenDist >= BodyTrace.MIN_TRACE_DISTANCE_PX_SQR;
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

    private void createColorIndex() {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        for (int i = 0; i < TRACE_MAX_SIZE; i++) {
            int a = 255 * i * i / TRACE_MAX_SIZE / TRACE_MAX_SIZE;
            Color tmpColor = new Color(r, g, b, a);
            colorIndex.add(tmpColor);
        }
    }

    public void updateZoom(int wheelRotation, double zoomIndex, int xPointLocationZoom, int yPointLocationZoom, Viewport viewport) {
        Body followBody = viewport.getFollowBody();
        if (followBody == null || followBody == this) return;
        for (BodyTraceFollowMode trace : tracesFollowMode) {
            trace.updateZoom(wheelRotation, zoomIndex, xPointLocationZoom, yPointLocationZoom, viewport);
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getViewR() {
        return viewR;
    }

    public void setViewR(int viewR) {
        this.viewR = viewR;
    }

    public void onUpdateFollowBody(Body followBody) {
        tracesFollowMode.clear();
    }
}
