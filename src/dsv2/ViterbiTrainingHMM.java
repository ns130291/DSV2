package dsv2;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author ns130291
 */
public class ViterbiTrainingHMM {

    public VectorsDoubleArray train(ArrayList<Vector[]> references, int modelLength) {
        System.out.println("Viterbi Training HMM\n================");
        //Lineare Zuordnung der Merkmalvektorfolgen zu den Zuständen des Modells
        ArrayList<Point[]> points = new ArrayList<>();
        int[][] uebergaenge = new int[modelLength][modelLength];
        
        //Für jede Merkmalvektorfolge
        for (Vector[] reference : references) {
            Point[] p = new Point[reference.length];
            //Steigung berechnen
            double slope = (double) (modelLength) / (double) (reference.length);
            System.out.println("Slope " + slope);
            
            //Für jeden Merkmalvektor in einer Merkmalvektorfolge
            for (int i = 0; i < reference.length; i++) {
                //int point = (int) Math.round(slope * i);
                //int point = ((int) Math.round(slope * (i + 1))) - 1;
                //int point = ((int) Math.round(slope * (i + 2))) - 1;
                //int point = (int) Math.round(slope * (i + 1) - 1);
                int point = (int) (slope * i);
                if (point > (modelLength - 1)) {
                    point = modelLength - 1;
                }
                if (point < 0) {
                    point = 0;
                }
                p[i] = new Point(i, point);
                if (i > 0) {
                    //TODO vereinfachen
                    uebergaenge[p[i - 1].y][p[i].y - p[i - 1].y + p[i - 1].y]++;
                }
            }
            points.add(p);

            Util.drawDiagram(p);
            System.out.println("");
        }

        System.out.println("\nÜbergänge");
        for (int i = 0; i < uebergaenge.length; i++) {
            for (int j = 0; j < uebergaenge[i].length; j++) {
                System.out.print(uebergaenge[i][j] + " ");
            }
            System.out.println("");
        }
        System.out.println("");
        //--> Übergangswahrscheinlichkeiten darauß berechnen  
        double[][] uebergangsw = new double[modelLength][modelLength];
        for (int i = 0; i < uebergaenge.length; i++) {
            //Zählen der Übergänge
            int num = 0;
            for (int j = 0; j < uebergaenge[i].length; j++) {
                num += uebergaenge[i][j];
            }
            for (int j = 0; j < uebergaenge[i].length; j++) {
                if (num == 0) {//Falls keine Übergänge
                    uebergangsw[i][j] = 0.01;
                } else {
                    uebergangsw[i][j] = (double) uebergaenge[i][j] / (double) num;
                }
                if (uebergangsw[i][j] <= 0) {
                    uebergangsw[i][j] = 0.01;
                }
                //Negativ Logarithmieren
                uebergangsw[i][j] = -1.0 * Math.log(uebergangsw[i][j]);
                if (uebergangsw[i][j] <= 0) {
                    uebergangsw[i][j] = 0.01;
                }
            }
        }
        System.out.println("\nÜbergangswahrscheinlichkeiten");
        for (int i = 0; i < uebergangsw.length; i++) {
            for (int j = 0; j < uebergangsw[i].length; j++) {
                System.out.print(uebergangsw[i][j] + " ");
            }
            System.out.println("");
        }
        System.out.println("");

        ArrayList<ArrayList<Vector>> models = new ArrayList<>(modelLength);
        for (int i = 0; i < modelLength; i++) {
            models.add(new ArrayList<Vector>());
        }
        //Sammeln der jeweiligen Zuordnungen
        for (int i = 0; i < references.size(); i++) {
            for (Point matching : points.get(i)) {
                models.get(matching.y).add(references.get(i)[matching.x]);
            }
        }

        //Kenndaten für jeden Zustand ausrechnen
        Vector[] mus = new Vector[modelLength];
        Vector[] sigmas = new Vector[modelLength];
        for (int i = 0; i < models.size(); i++) {
            Vector mu = Util.arithmeticMean(models.get(i).toArray(new Vector[models.get(i).size()]));
            System.out.print("µ = ");
            System.out.println(mu);
            Vector sigma = Util.variance(models.get(i).toArray(new Vector[models.get(i).size()]), mu);
            System.out.print("σ² = ");
            System.out.println(sigma);
            System.out.println("");
            mus[i] = mu;
            sigmas[i] = sigma;
        }

        System.out.println("\nIterieren\n=============");

        double sum = Double.POSITIVE_INFINITY;
        double newSum = Double.MAX_VALUE;
        VectorsDoubleArray result = null;
        int i = 1;
        while (sum - newSum != 0) {
            sum = newSum;
            /*if (i > 100) {
                throw new RuntimeException("Wahrscheinlich gabs nen Fehler...");
            }*/
            System.out.println("Schritt " + i);
            System.out.println("------------");
            i++;
            VectorsDoubleArray vd = iterationStep(mus, sigmas, references, uebergangsw);
            mus = vd.getVectors1();
            sigmas = vd.getVectors2();
            newSum = vd.getDouble();
            result = vd;
            System.out.println("");
        }
        
        //µ, σ², Summe, Übergangswahrscheinlichkeiten
        return result;
    }

    private VectorsDoubleArray iterationStep(Vector[] mus, Vector[] sigmas, ArrayList<Vector[]> references, double[][] uebergangsw) {

        //Initialisierung des Viterbi-Algorithmus mit passender Distanzfunktion
        ViterbiHMM viterbi = new ViterbiHMM(new MahalanobisDistance());

        double sum = 0;
        ArrayList<Point[]> points = new ArrayList<>();
        int[][] uebergaenge = new int[mus.length][mus.length];
        for (Vector[] reference : references) {
            PointsDouble pd = viterbi.calc(reference, mus, sigmas, uebergangsw);
            //Sammeln der Zuordnungen
            points.add(pd.getPoints());
            
            //Gesamtsumme erhöhen
            sum += pd.getDouble();

            //print matching
            Point[] p = pd.getPoints();

            for (int i = 1; i < reference.length; i++) {
                uebergaenge[p[i - 1].y][p[i].y - p[i - 1].y + p[i - 1].y]++;
            }
            
            Util.drawDiagram(p);
        }

        System.out.println("\nÜbergänge");
        for (int i = 0; i < uebergaenge.length; i++) {
            for (int j = 0; j < uebergaenge[i].length; j++) {
                System.out.print(uebergaenge[i][j] + " ");
            }
            System.out.println("");
        }
        System.out.println("");
        
        //--> Übergangswahrscheinlichkeiten darauß berechnen  
        uebergangsw = new double[mus.length][mus.length];
        for (int i = 0; i < uebergaenge.length; i++) {
            int num = 0;
            for (int j = 0; j < uebergaenge[i].length; j++) {
                num += uebergaenge[i][j];
            }
            for (int j = 0; j < uebergaenge[i].length; j++) {
                if (num == 0) {
                    uebergangsw[i][j] = 0.01;
                } else {
                    uebergangsw[i][j] = (double) uebergaenge[i][j] / (double) num;
                }
                if (uebergangsw[i][j] <= 0) {
                    uebergangsw[i][j] = 0.01;
                }
                /*uebergangsw[i][j] = -1.0 * Math.log(uebergangsw[i][j]);
                 if (uebergangsw[i][j] <= 0) {
                 uebergangsw[i][j] = 0.01;
                 }*/
            }
        }
        //TODO: Vermutlich noch die neuen Übergangswahrscheinlichkeiten ausrechnen
        //beim letzten Iterationsschritt muss vermutlich nicht mehr logarithmiert 
        //werden! (Angaben können dann mit der Lösung überprüft werden)
        System.out.println("\nÜbergangswahrscheinlichkeiten");
        for (int i = 0; i < uebergangsw.length; i++) {
            for (int j = 0; j < uebergangsw[i].length; j++) {
                System.out.print(uebergangsw[i][j] + " ");
            }
            System.out.println("");
        }
        System.out.println("");

        //TEST
        for (int i = 0; i < uebergaenge.length; i++) {
            for (int j = 0; j < uebergaenge[i].length; j++) {
                uebergangsw[i][j] = -1.0 * Math.log(uebergangsw[i][j]);
                if (uebergangsw[i][j] <= 0) {
                    uebergangsw[i][j] = 0.01;
                }
            }
        }
        //TEST

        ArrayList<ArrayList<Vector>> models = new ArrayList<>(mus.length);
        for (int i = 0; i < mus.length; i++) {
            models.add(new ArrayList<Vector>());
        }
        //Sammeln der jeweiligen Zuordnungen
        for (int i = 0; i < references.size(); i++) {
            for (Point matching : points.get(i)) {
                models.get(matching.y).add(references.get(i)[matching.x]);
            }
        }

        //Kenndaten berechnen
        mus = new Vector[mus.length];
        sigmas = new Vector[sigmas.length];
        for (int i = 0; i < models.size(); i++) {
            Vector mu = Util.arithmeticMean(models.get(i).toArray(new Vector[models.get(i).size()]));
            System.out.print("µ = ");
            System.out.println(mu);
            Vector sigma = Util.variance(models.get(i).toArray(new Vector[models.get(i).size()]), mu);
            System.out.print("σ² = ");
            System.out.println(sigma);
            System.out.println("");
            mus[i] = mu;
            sigmas[i] = sigma;
        }
        System.out.println("Sum " + Util.r2d(sum) + "\n");

        return new VectorsDoubleArray(mus, sigmas, sum, uebergangsw);
    }
}
