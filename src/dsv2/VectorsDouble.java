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
    Vector[] vectors;
    double doub;

    public VectorsDouble(Vector[] vectors, double doub) {
        this.vectors = vectors;
        this.doub = doub;
    }

    public Vector[] getVectors() {
        return vectors;
    }

    public double getDouble() {
        return doub;
    }
    
    
}
