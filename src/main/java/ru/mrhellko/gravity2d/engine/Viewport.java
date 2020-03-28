package ru.mrhellko.gravity2d.engine;

import ru.mrhellko.gravity2d.entity.Body;
import ru.mrhellko.gravity2d.ui.Form;

public class Viewport {
    private double cellX = Form.A_E;
    private double cellY = Form.A_E;

    private double left = -cellX * 6;
    private double right = cellX * 6;
    private double top = - cellY * 4;
    private double down = cellY * 4;
    private int width = 1200;
    private int height = 800;
    private Body followBody = null;

    public double getRealX(int x) {
        return left + (right - left) * x / width;
    }

    public double getRealY(int y) {
        return top + (down - top) * y / height;
    }

    public double getRealDiffX(int x) {
        return (right - left) * x / width;
    }

    public double getRealDiffY(int y) {
        return (down - top) * y / height;
    }

    public int getScreenX(double x) {
        return (int) ((x - left) / (right - left) * width);
    }

    public int getScreenY(double y) {
        return (int) ((y - top) / (down - top) * height);
    }

    public void updateZoom(int wheelRotation, double zoomIndex, int xPointLocationZoom, int yPointLocationZoom) {
        if (wheelRotation < 0) {
            zoomIndex = 1 / zoomIndex;
        }
        double xRealPointLocationZoom = getRealX(xPointLocationZoom);
        double yRealPointLocationZoom = getRealY(yPointLocationZoom);
        if (followBody != null) {
            xRealPointLocationZoom = followBody.getX();
            yRealPointLocationZoom = followBody.getY();
        }
        double newLeft = zoomIndex * this.left + (1 - zoomIndex) * xRealPointLocationZoom;
        double newRight = zoomIndex * this.right + (1 - zoomIndex) * xRealPointLocationZoom;
        double newTop = zoomIndex * this.top + (1 - zoomIndex) * yRealPointLocationZoom;
        double newDown = zoomIndex * this.down + (1 - zoomIndex) * yRealPointLocationZoom;

        this.left = newLeft;
        this.right = newRight;
        this.top = newTop;
        this.down = newDown;
    }

    public void updateFollowMode() {
        if (followBody == null) return;

        double deltaX = followBody.getX() - (left + (right - left) / 2.0);
        double deltaY = followBody.getY() - (top + (down - top) / 2.0);
        this.left += deltaX;
        this.right += deltaX;
        this.top += deltaY;
        this.down += deltaY;
    }

    public Body getFollowBody() {
        return followBody;
    }

    public void setFollowBody(Body followBody) {
        this.followBody = followBody;
    }

    public int getCenterScreenX() {
        return width / 2;
    }

    public int getCenterScreenY() {
        return height / 2;
    }

    public void moveBorders(int diffX, int diffY) {
        if (followBody != null) return;
        double diffRealX = getRealDiffX(diffX);
        double diffRealY = getRealDiffY(diffY);
        this.left -= diffRealX;
        this.right -= diffRealX;
        this.top -= diffRealY;
        this.down -= diffRealY;
    }
}
