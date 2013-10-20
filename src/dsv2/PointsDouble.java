/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dsv2;

import java.awt.Point;

/**
 *
 * @author ns130291
 */
public class PointsDouble {
    Point[] points;
    double doub;

    public PointsDouble(Point[] points, double doub) {
        this.points = points;
        this.doub = doub;
    }

    public Point[] getPoints() {
        return points;
    }

    public double getDouble() {
        return doub;
    }
    
    
}
