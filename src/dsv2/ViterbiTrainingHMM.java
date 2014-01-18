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
        /*int[] lengths = new int[references.size()];
         for (int i = 0; i < lengths.length; i++) {
         lengths[i] = references.get(i).length;
         }*/

        //TODO: min length of array == 1
        /*Arrays.sort(lengths);
         int median;
         if (lengths.length % 2 == 1) {
         median = lengths[lengths.length / 2];
         } else {
         median = (lengths[lengths.length / 2] + lengths[lengths.length / 2 - 1]) / 2;
         }
         //Aufgabenstellung sagt 4!
         median = 4;
         System.out.println("Median: " + median);
         //int modelLength = median;// / 2;*/
        //Lineare Zuordnung der Merkmalvektorfolgen zu den Zuständen des Modells
        ArrayList<Point[]> points = new ArrayList<>();
        int[][] uebergaenge = new int[modelLength][modelLength];
        for (Vector[] reference : references) {
            Point[] p = new Point[reference.length];
            //double slope = (double) (modelLength - 1) / (double) (reference.length - 1);
            double slope = (double) (modelLength) / (double) (reference.length);
            System.out.println("Slope " + slope);
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
                    //System.out.println(p[i - 1].y + " " + (p[i].y - p[i - 1].y + p[i - 1].y));
                    uebergaenge[p[i - 1].y][p[i].y - p[i - 1].y + p[i - 1].y]++;
                }
            }
            points.add(p);

            //print matching
            /*System.out.println("a x");
            for (int i = 0; i < p.length; i++) {
                System.out.println(p[i].x + " " + p[i].y);
            }*/
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
        //TODO: Nullen durch 0.01 ersetzen
        //TODO: Negativer Log anwenden
        double[][] uebergangsw = new double[modelLength][modelLength];
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

        //Sammeln der jeweiligen Zuordnungen
        ArrayList<ArrayList<Vector>> models = new ArrayList<>(modelLength);
        for (int i = 0; i < modelLength; i++) {
            models.add(new ArrayList<Vector>());
        }
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
            VectorsDoubleArray vd = iterate(mus, sigmas, references, uebergangsw);
            mus = vd.getVectors1();
            sigmas = vd.getVectors2();
            newSum = vd.getDouble();
            result = vd;
            System.out.println("");
        }

        return result;
    }

    private VectorsDoubleArray iterate(Vector[] mus, Vector[] sigmas, ArrayList<Vector[]> references, double[][] uebergangsw) {

        ViterbiHMM viterbi = new ViterbiHMM(new LogarithmisedNormalDistributionDistance());

        double sum = 0;
        ArrayList<Point[]> points = new ArrayList<>();
        int[][] uebergaenge = new int[mus.length][mus.length];
        for (Vector[] reference : references) {
            PointsDouble pd = viterbi.calc(reference, mus, sigmas, uebergangsw);
            points.add(pd.getPoints());
            sum += pd.getDouble();

            //print matching
            Point[] p = pd.getPoints();

            for (int i = 1; i < reference.length; i++) {
                //System.out.println(p[i].y + " " +p[i-1].y + " " + (p[i].y - p[i-1].y + p[i-1].y));
                uebergaenge[p[i - 1].y][p[i].y - p[i - 1].y + p[i - 1].y]++;

            }
            /*System.out.println("Viterbi Punkte");
             for (int i = 0; i < p.length; i++) {
             System.out.println(p[i].x + " " + p[i].y);
             }*/
            Util.drawDiagram(p);
            //System.out.println("Summe " + Util.r2d(pd.getDouble()) + "\n");

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
        //TODO: Nullen durch 0.01 ersetzen
        //TODO: Negativer Log anwenden
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

        //Sammeln der jeweiligen Zuordnungen
        ArrayList<ArrayList<Vector>> models = new ArrayList<>(mus.length);
        for (int i = 0; i < mus.length; i++) {
            models.add(new ArrayList<Vector>());
        }
        for (int i = 0; i < references.size(); i++) {
            for (Point matching : points.get(i)) {
                models.get(matching.y).add(references.get(i)[matching.x]);
            }
        }

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
