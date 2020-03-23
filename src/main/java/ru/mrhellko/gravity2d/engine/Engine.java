package ru.mrhellko.gravity2d.engine;

import ru.mrhellko.gravity2d.entity.Body;

import java.util.ArrayList;
import java.util.List;

public class Engine implements Runnable {
    private static double G = 6.67E-11;
    private long deltaT;
    private List<Body> bodyList = new ArrayList<>();
    private long time = 0;

    public long getTime() {
        return time;
    }

    public void addBody(Body body) {
        bodyList.add(body);
    }

    public List<Body> getBodyList() {
        return bodyList;
    }


    private void startSimulation(long deltaT) {
        while(true) {
            process();
        }
    }

    private void process() {
        time += deltaT;
        for(Body current : bodyList) {
            for(Body body : bodyList) {
                if(body.equals(current)) continue;
                gravity(current, body);
            }
        }
    }

    private void gravity(Body current, Body body) {
        double r = current.distanceFrom(body);
        double r2 = current.squareDistanceFrom(body);
        double F = G * current.getM() * body.getM() / r2;
        double Fx = F * current.deltaX(body) / r;
        double Fy = F * current.deltaY(body) / r;
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
        startSimulation(deltaT);
    }

    public long getDeltaT() {
        return deltaT;
    }

    public void setDeltaT(long deltaT) {
        this.deltaT = deltaT;
    }
}
