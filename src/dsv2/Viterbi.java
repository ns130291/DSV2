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
    
    public void calc(Vector[] a, Vector[] x) throws Exception{
        double[][] s = new double[x.length][a.length];
        int[][] r = new int[x.length][a.length];
        
        //---Erste Spalte---
        s[0][0] = d.calcDistance(a[0], x[0]);
        for(int j = 1; j < a.length; j++){
            s[0][j] = Double.POSITIVE_INFINITY;
        }
        
        //---Iteriere über Spalten---
        for(int i = 1; i < x.length; i++){
            for(int j = 0; j < a.length; j++){
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
                s[i][j] = distance + d.calcDistance(x[i], a[j]);
            }
        }
        
        //---Rückwärtszeiger verfolgen---
        Point[] p = new Point[x.length];
        int j = a.length - 1;
        for(int i = x.length - 1; i >= 0; i--){
            p[i] = new Point(i, j);
            j -= r[i][j];
        }
        
        System.out.println("a x");
        for(int i = 0; i < p.length; i++){            
            System.out.println(p[i].x + " " + p[i].y);
        }
        System.out.println("");
        
        //todo: Überprüfung ob n-1 m-1 Knoten Minimum ist??? Vermutlich nicht, man nimmt einfach den n-1 m-1 Knoten?
        for (j = a.length - 1; j >= 0; j--) {
            for (int i = 0; i < x.length; i++) {
                System.out.print(s[i][j] + " ");
            }
            System.out.println("");
        }
    }
}
