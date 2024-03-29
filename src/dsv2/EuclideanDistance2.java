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

    /**
     * 
     * @param x 
     * @param mu µ(Mittelwert) des Modells 
     * @param sigma2 
     * @return
     * @throws RuntimeException 
     */
    @Override
    public double calcDistance(Vector x, Vector mu, Vector sigma2) throws RuntimeException {
        if(x.getSize() != mu.getSize()){
            throw new RuntimeException("Vectors have different sizes");
        }else{
            double[] xs = x.getValues();
            double[] mus = mu.getValues();
            
            double distance = 0;
            
            for(int i = 0; i < xs.length; i++){
                distance += Math.pow(xs[i]-mus[i], 2);
            }
            return distance;
        }
    }
    
}
