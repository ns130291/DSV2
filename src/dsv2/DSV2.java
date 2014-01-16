/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsv2;

import java.util.ArrayList;

/**
 *
 * @author ns130291
 */
public class DSV2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*Vector eins = new Vector(new double[]{4, 1, -2});
        Vector zwei = new Vector(new double[]{2, 3, -1});
        
        Distance d = new EuclideanDistance2();
        double dis = -1;
            
        dis = d.calcDistance(eins, zwei, null);
        
        //System.out.println("Distanz: " + dis); 
        System.out.println("");
        
        
        Vector[] a = new Vector[]{new Vector(new double[]{9, 0 ,1}), new Vector(new double[]{6, 2, 1}), new Vector(new double[]{4, 2, 1}), new Vector(new double[]{1, 0 ,2})};
        Vector[] x = new Vector[]{new Vector(new double[]{9, 1, 1}), new Vector(new double[]{8, 1, 0}), new Vector(new double[]{4, 2, 1}), new Vector(new double[]{4, 3, 1}), new Vector(new double[]{3, 2, 0}), new Vector(new double[]{2, 0, 1})};
        
        Viterbi viterbi = new Viterbi(new EuclideanDistance2());
        PointsDouble matching = viterbi.calc(x, a, null);
        
        /*System.out.println("Zuordnung");
        for(Point p1:matching.getPoints()){
            System.out.println(p1.x + " " + p1.y);
        }
        System.out.println("");*/
        
        
        //Aufgabe 2
        /*System.out.println("Aufgabe 2");
        
        String path = "C:\\Users\\ns130291\\Desktop\\Java\\DSV2\\daten.txt";
        
        Input i = new Input();
        ArrayList<Vector[]> references =  i.fromFile(path);
        
        for(Vector[] xx:references){
            for(Vector xxx:xx){
                System.out.println(xxx);
            }
            System.out.println("");
        }
        
        ViterbiTraining viterbiTraining = new ViterbiTraining();
        viterbiTraining.train(references);*/
        
        System.out.println("Aufgabe 4");
        
        String path2 = "C:\\Users\\ns130291\\Desktop\\Java\\DSV2\\daten2.txt";
        Input i2 = new Input();
        ArrayList<Vector[]> references2 =  i2.fromFile(path2);
        
        for(Vector[] xx:references2){
            for(Vector xxx:xx){
                System.out.println(xxx);
            }
            System.out.println("");
        }
        ViterbiTrainingHMM viterbiTraining2 = new ViterbiTrainingHMM();
        viterbiTraining2.train(references2);
        
        //Util.muSigma(new double[]{2, 6, 1, 5, 7});
    }

}
