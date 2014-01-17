package dsv2;

import java.awt.Point;

/**
 *
 * @author ns130291
 */
public class ViterbiHMM {
    
    private Distance d;

    public ViterbiHMM(Distance d) {
        this.d = d;
    }    
    
    public PointsDouble calc(Vector[] x, Vector[] mu, Vector[] sigma2, double[][] uebergangsw){
        double[][] s = new double[x.length][mu.length];
        int[][] r = new int[x.length][mu.length];
        
        //---Erste Spalte---
        s[0][0] = 1;//d.calcDistance(mu[0], x[0], sigma2[0]);
        for(int j = 1; j < mu.length; j++){
            s[0][j] = Double.POSITIVE_INFINITY;
        }
        
        //---Iteriere über Spalten---
        for(int i = 1; i < x.length; i++){
            for(int j = 0; j < mu.length; j++){
                
                // k=0
                //System.out.println("i-1 " + (i-1) + " j " + j);
                double min = s[i-1][j] + uebergangsw[j][j];        
                //double distance = s[i-1][j];
                int k = j;
                // k=1
                if(j > 0){              
                    if(s[i-1][j-1] + uebergangsw[j-1][j] < min){
                        min = s[i-1][j-1] + uebergangsw[j-1][j];
                        k=j-1;
                    }
                }
                // k=2
                if(j > 1){                  
                    if(s[i-1][j-2] + uebergangsw[j-2][j] < min){
                        min = s[i-1][j-2] + uebergangsw[j-2][j];
                        k=j-2;
                    }
                }
                
                r[i][j] = k;
                s[i][j] = min + d.calcDistance(x[i], mu[j], sigma2[j]);
            }
        }
        
        /*System.out.println("\nRückwärtszeiger");
        for(int i = 0; i < r.length; i++){
            for(int j = 0; j < r[i].length; j++){
                System.out.print(r[i][j] + " ");
            }
            System.out.println("");
        }
        System.out.println("");*/
        
        //---Rückwärtszeiger verfolgen---
        //TODO: hier
        Point[] p = new Point[x.length];
        int j = mu.length - 1;//Endzustand
        for(int i = x.length - 1; i >= 0; i--){
            p[i] = new Point(i, j);
            //j -= r[i][j];
            j = r[i][j];
        }
        
        /*System.out.println("a x");
        for (Point p1 : p) {
            System.out.println(p1.x + " " + p1.y);
        }
        System.out.println("");*/
        
        //todo: Überprüfung ob n-1 m-1 Knoten Minimum ist??? Vermutlich nicht, man nimmt einfach den n-1 m-1 Knoten?
        /*for (j = model.length - 1; j >= 0; j--) {
            for (int i = 0; i < x.length; i++) {
                System.out.print(s[i][j] + " ");
            }
            System.out.println("");
        }*/
        
        return new PointsDouble(p, s[x.length - 1][mu.length - 1]);
    }
}
