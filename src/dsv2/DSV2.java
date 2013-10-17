/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsv2;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ns130291
 */
public class DSV2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Vector eins = new Vector(new double[]{4, 1, -2});
        Vector zwei = new Vector(new double[]{2, 3, -1});
        
        Distance d = new EuclideanDistance2();
        double dis = -1;
        try {
            dis = d.calcDistance(eins, zwei);
        } catch (Exception ex) {
            Logger.getLogger(DSV2.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Distanz: " + dis); 
        
        
        Vector[] a = new Vector[]{new Vector(new double[]{9, 0 ,1}), new Vector(new double[]{6, 2, 1}), new Vector(new double[]{4, 2, 1}), new Vector(new double[]{1, 0 ,2})};
        Vector[] x = new Vector[]{new Vector(new double[]{9, 1, 1}), new Vector(new double[]{8, 1, 0}), new Vector(new double[]{4, 2, 1}), new Vector(new double[]{4, 3, 1}), new Vector(new double[]{3, 2, 0}), new Vector(new double[]{2, 0, 1})};
        
        Viterbi viterbi = new Viterbi(new EuclideanDistance2());
        try {
            viterbi.calc(a, x);
        } catch (Exception ex) {
            Logger.getLogger(DSV2.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String path = "C:\\Users\\ns130291\\Desktop\\Java\\DSV2\\daten.txt";
        
        Input i = new Input();
        ArrayList<Vector[]> references =  i.fromFile(path);
        
        for(Vector[] xx:references){
            for(Vector xxx:xx){
                System.out.println(xxx);
            }
            System.out.println("");
        }
    }

}