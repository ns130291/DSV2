/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dsv2;

/**
 *
 * @author ns130291
 */
public class VectorsDouble {
    private Vector[] vectors1;
    private Vector[] vectors2;
    private double doub;

    public VectorsDouble(Vector[] vectors1, Vector[] vectors2, double doub) {
        this.vectors1 = vectors1;
        this.vectors1 = vectors2;
        this.doub = doub;
    }

    public Vector[] getVectors1() {
        return vectors1;
    }

    public Vector[] getVectors2() {
        return vectors1;
    }

    public double getDouble() {
        return doub;
    }
    
    
}
