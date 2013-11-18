package dsv2;

import java.awt.Point;

/**
 *
 * @author ns130291
 */
public class Viterbi {
    
    private Distance d;

    public Viterbi(Distance d) {
        this.d = d;
    }    
    
    public PointsDouble calc(Vector[] x, Vector[] mu, Vector[] sigma2){
        double[][] s = new double[x.length][mu.length];
        int[][] r = new int[x.length][mu.length];
        
        //---Erste Spalte---
        s[0][0] = d.calcDistance(mu[0], x[0], null);
        for(int j = 1; j < mu.length; j++){
            s[0][j] = Double.POSITIVE_INFINITY;
        }
        
        //---Iteriere über Spalten---
        for(int i = 1; i < x.length; i++){
            for(int j = 0; j < mu.length; j++){
                // k=0
                double distance = s[i-1][j];
                int k = 0;
                // k=1
                if(j > 0){              
                    if(s[i-1][j-1] < distance){
                        distance = s[i-1][j-1];
                        k=1;
                    }
                }
                // k=2
                if(j > 1){                  
                    if(s[i-1][j-2] < distance){
                        k=2;
                        distance = s[i-1][j-2];
                    }
                }
                
                r[i][j] = k;
                s[i][j] = distance + d.calcDistance(x[i], mu[j], null);
            }
        }
        
        //---Rückwärtszeiger verfolgen---
        Point[] p = new Point[x.length];
        int j = mu.length - 1;
        for(int i = x.length - 1; i >= 0; i--){
            p[i] = new Point(i, j);
            j -= r[i][j];
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
