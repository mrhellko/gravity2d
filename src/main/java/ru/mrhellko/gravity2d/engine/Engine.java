package ru.mrhellko.gravity2d.engine;

import ru.mrhellko.gravity2d.entity.Body;
import ru.mrhellko.gravity2d.ui.Form;

import java.util.ArrayList;
import java.util.List;

public class Engine implements Runnable {
    private static double G = 6.67E-11;
    private double deltaT;
    private int countsPerFramePlan = Form.COUNTS_PER_FRAME_MAX;
    private int countsPerFrameReal = 0;
    private List<Body> bodyList = new ArrayList<>();
    private double time = 0;
    private final Object lock = new Object();
    private boolean isFinishedCalc = false;

    public double getTime() {
        return time;
    }

    public void addBody(Body body) {
        bodyList.add(body);
    }

    public List<Body> getBodyList() {
        return bodyList;
    }


    private void startSimulation() {
        while (true) {
            if (isFinishedCalc) {
                synchronized (lock) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            while (countsPerFrameReal < countsPerFramePlan) {
                process();
                countsPerFrameReal++;
                isFinishedCalc = true;
            }
        }
    }

    private void process() {
        time += deltaT;
        for(Body current : bodyList) {
            double Fx = 0;
            double Fy = 0;
            for(Body body : bodyList) {
                if(body.equals(current)) continue;
                double r = current.distanceFrom(body);
                double r2 = current.squareDistanceFrom(body);
                double F = G * current.getM() * body.getM() / r2;
                Fx += F * current.deltaX(body) / r;
                Fy += F * current.deltaY(body) / r;
            }
            gravity(current, Fx, Fy);
        }
    }

    private void gravity(Body current, double Fx, double Fy) {
        double ax = Fx / current.getM();
        double ay = Fy / current.getM();
        double vx = current.getVx() + ax * deltaT;
        double vy = current.getVy() + ay * deltaT;
        double x = current.getX() + vx * deltaT + ax / 2.0 * deltaT * deltaT;
        double y = current.getY() + vy * deltaT + ay / 2.0 * deltaT * deltaT;
        current.setNewValues(x, y, vx, vy, Fx, Fy);
    }

    @Override
    public void run() {
        startSimulation();
    }

    public double getDeltaT() {
        return deltaT;
    }

    public void setDeltaT(double deltaT) {
        this.deltaT = deltaT;
    }

    public int getCountsPerFrame() {
        return countsPerFrameReal;
    }

    double a = Math.pow(10, 0.125);
    public void setCountsPerFrame(int countsPerFramePlan) {
        if (countsPerFramePlan == 0) {
            this.countsPerFramePlan = 0;
        } else {
            this.countsPerFramePlan = (int) Math.pow(a, countsPerFramePlan);
        }
    }

    public void updateRealCounts() {
        synchronized (lock) {
            countsPerFrameReal = 0;
            isFinishedCalc = false;
            lock.notify();
        }
    }

    public void updateZoom(int wheelRotation, double zoomIndex, int xPointLocationZoom, int yPointLocationZoom, Viewport viewport) {
        for (Body body : bodyList) {
            body.updateZoom(wheelRotation, zoomIndex, xPointLocationZoom, yPointLocationZoom, viewport);
        }
    }

    public void onUpdateFollowBody(Body followBody) {
        for (Body body : bodyList) {
            body.onUpdateFollowBody(followBody);
        }
    }
}
