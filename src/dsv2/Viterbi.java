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
    
    public void calc(Vector[] x, Vector[] a) throws Exception{
        double[][] s = new double[a.length][x.length];
        int[][] r = new int[a.length][x.length];
        
        //---Erste Spalte---
        s[0][0] = d.calcDistance(x[0], a[0]);
        for(int j = 1; j < x.length; j++){
            s[0][j] = Double.POSITIVE_INFINITY;
        }
        
        //---Iteriere über Spalten---
        for(int i = 1; i < a.length; i++){
            for(int j = 0; j < x.length; j++){
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
                s[i][j] = distance + d.calcDistance(a[i], x[j]);
            }
        }
        
        //---Rückwärtszeiger verfolgen---
        Point[] p = new Point[a.length];
        int j = x.length - 1;
        for(int i = a.length - 1; i >= 0; i--){
            p[i] = new Point(i, j);
            j -= r[i][j];
        }
        
        System.out.println("a x");
        for(int i = 0; i < p.length; i++){            
            System.out.println(p[i].x + " " + p[i].y);
        }
        System.out.println("");
        
        //todo: Überprüfung ob n-1 m-1 Knoten Minimum ist??? Vermutlich nicht, man nimmt einfach den n-1 m-1 Knoten?
        for (j = x.length - 1; j >= 0; j--) {
            for (int i = 0; i < a.length; i++) {
                System.out.print(s[i][j] + " ");
            }
            System.out.println("");
        }
    }
}
