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
                    System.out.println("x1 " + points[i].y + " x2 " + points[i - 1].y + " = " + (points[i].y - points[i - 1].y));
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
}
