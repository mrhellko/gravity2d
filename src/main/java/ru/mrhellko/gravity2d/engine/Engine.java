package ru.mrhellko.gravity2d.engine;

import ru.mrhellko.gravity2d.entity.Body;

import java.util.ArrayList;
import java.util.List;

public class Engine {
    public static double G = 6.67E-11;

    List<Body> bodyList = new ArrayList<>();
    long time = 0;

    public void addBody(Body body) {
        bodyList.add(body);
    }

    public void process(long deltaT) {
        time += deltaT;
        for(Body current : bodyList) {
            for(Body body : bodyList) {
                if(body.equals(current)) continue;
                gravity(current, body, deltaT);
            }
        }
    }

    public void gravity(Body current, Body body, long deltaT) {
        double r = current.distanceFrom(body);
        double r2 = current.squareDistanceFrom(body);
        double F = G * current.getM() * body.getM() / r2;
        double signX = current.signX(body);
        double signY = current.signY(body);
        double Fx = signX * F * current.deltaX(body) / r;
        double Fy = signY * F * current.deltaY(body) / r;
        double ax = Fx / current.getM();
        double ay = Fy / current.getM();
        double vx = current.getVx() + ax * deltaT;
        double vy = current.getVy() + ay * deltaT;
        double x = current.getX() + vx * deltaT + ax / 2.0 * deltaT * deltaT;
        double y = current.getY() + vy * deltaT + ay / 2.0 * deltaT * deltaT;
        current.setNewValues(x, y, vx, vy, Fx, Fy);
    }
}
