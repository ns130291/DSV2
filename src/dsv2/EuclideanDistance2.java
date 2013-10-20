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
public class EuclideanDistance2 implements Distance{

    @Override
    public double calcDistance(Vector v1, Vector v2) throws RuntimeException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if(v1.getSize() != v2.getSize()){
            throw new RuntimeException("Vectors have different sizes");
        }else{
            double[] values1 = v1.getValues();
            double[] values2 = v2.getValues();
            
            double distance = 0;
            
            for(int i = 0; i < values1.length; i++){
                distance += Math.pow(values1[i]-values2[i], 2);
            }
            return distance;
        }
    }
    
}
