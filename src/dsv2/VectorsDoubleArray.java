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
public class VectorsDoubleArray {
    private Vector[] vectors1;
    private Vector[] vectors2;
    private double doub;
    private double[][] array;

    public VectorsDoubleArray(Vector[] vectors1, Vector[] vectors2, double doub, double[][] array) {
        this.vectors1 = vectors1;
        this.vectors2 = vectors2;
        this.doub = doub;
        this.array = array;
    }

    public Vector[] getVectors1() {
        return vectors1;
    }

    public Vector[] getVectors2() {
        return vectors2;
    }

    public double getDouble() {
        return doub;
    }

    public double[][] getArray() {
        return array;
    }
    
    
}
