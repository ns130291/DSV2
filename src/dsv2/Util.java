/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsv2;

import java.awt.Point;

/**
 *
 * @author ns130291
 */
public class Util {

    /**
     * Rounds doubles to two decimal points
     * @param in double value to round
     * @return rounded double value
     */
    public static double r2d(double in) {
        return Math.round(in * 100) / 100.0;
    }

    public static void drawDiagram(Point[] points) {
        int max = 0;
        for (int i = 0; i < points.length; i++) {
            if (points[i].y > max) {
                max = points[i].y;
            }
        }
        String[] lines = new String[max + 1];
        for (int i = 0; i < lines.length; i++) {
            lines[i] = new String();
        }
        for (int i = 0; i < points.length; i++) {
            int leerzeichen = points[i].x * 2;
            if (i > 0) {
                leerzeichen--;
            }
            leerzeichen = leerzeichen - lines[points[i].y].length();
            for (int j = 0; j < leerzeichen; j++) {
                lines[points[i].y] += " ";

            }
            if (i > 0) {
                if (points[i - 1].y == points[i].y) {
                    lines[points[i].y] += "-";
                } else {
                    if (points[i].y - points[i - 1].y == 2) {
                        leerzeichen = points[i].x * 2 - 1;
                        for (int j = 0; j < leerzeichen; j++) {
                            lines[points[i].y - 1] += " ";

                        }
                        lines[points[i].y - 1] += "/";
                    }
                    lines[points[i].y] += " ";

                }
            }
            lines[points[i].y] += "x";
        }

        //Ausgeben
        System.out.println("\nDiagramm");
        System.out.println(" a");
        for (int i = lines.length - 1; i >= 0; i--) {
            System.out.println(i + "|" + lines[i]);
        }
        System.out.print("-+");
        for (int i = 0; i < points.length * 2 - 1; i++) {
            System.out.print("-");
        }
        System.out.println("x");
        System.out.print(" |");
        for (int i = 0; i < points.length; i++) {
            System.out.print(i + " ");
        }
        System.out.println("");
    }

    public static void muSigma(double[] values) {
        double sum = 0;
        double sumquad = 0;
        for (int i = 0; i < values.length; i++) {
            sum += values[i];
            sumquad += values[i] * values[i];
        }

        double mu = sum / values.length;
        double varianz = sumquad / values.length - mu * mu;
        System.out.println("Müüühh = " + mu + " Sigma = " + varianz);
    }
    
    public static Vector arithmeticMean(Vector[] model){
        Vector mu = new Vector(new double[]{0, 0, 0});
        for(Vector m:model){
            mu = Vector.add(mu, m);
        }
        mu.divide(model.length);        
        return mu;
    }
    
    public static Vector variance(Vector[] model, Vector mu){
        Vector sigma = new Vector(new double[]{0, 0, 0});
        for(Vector m:model){
            sigma = Vector.add(sigma, Vector.multiply(m, m));
        }
        sigma.divide(model.length);
        sigma = Vector.subtract(sigma, Vector.multiply(mu, mu));
        //TODO wenn kleiner 0,01 auf 0,01 setzen
        double[] vals = sigma.getValues();
        for(int i = 0; i < vals.length; i++){
            if(vals[i] < 0.01){
                vals[i] = 0.01;
            }
        }
        sigma.setValues(vals);
        return sigma;
    }
}
