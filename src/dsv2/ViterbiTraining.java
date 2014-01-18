package dsv2;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author ns130291
 */
public class ViterbiTraining {

    public void train(ArrayList<Vector[]> references) {
        System.out.println("Viterbi Training\n================");
        int[] lengths = new int[references.size()];
        for (int i = 0; i < lengths.length; i++) {
            lengths[i] = references.get(i).length;
        }

        //TODO: min length of array == 1
        Arrays.sort(lengths);
        int median;
        if (lengths.length % 2 == 1) {
            median = lengths[lengths.length / 2];
        } else {
            median = (lengths[lengths.length / 2] + lengths[lengths.length / 2 - 1]) / 2;
        }
        //Aufgabenstellung sagt 4!
        median = 4;
        System.out.println("Median: " + median);
        int modelLength = median;// / 2;

        ArrayList<Point[]> points = new ArrayList<>();
        for (Vector[] reference : references) {
            Point[] p = new Point[reference.length];
            double slope = (double) (modelLength - 1) / (double) (reference.length - 1);
            for (int i = 0; i < reference.length; i++) {
                p[i] = new Point(i, (int) Math.round(slope * i));
            }
            points.add(p);

            //print matching
            System.out.println("a x");
             for (int i = 0; i < p.length; i++) {
             System.out.println(p[i].x + " " + p[i].y);
             }
            Util.drawDiagram(p);
            System.out.println("");
        }

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
        int i = 1;
        while (sum - newSum != 0) {
            sum = newSum;
            System.out.println("Schritt " + i);
            System.out.println("------------");
            i++;
            VectorsDoubleArray vd = iterate(mus, sigmas, references);
            mus = vd.getVectors1();
            sigmas = vd.getVectors2();
            newSum = vd.getDouble();
            System.out.println("");
        }

    }

    private VectorsDoubleArray iterate(Vector[] mus, Vector[] sigmas, ArrayList<Vector[]> references) {

        Viterbi viterbi = new Viterbi(new SimplifiedNormalDistributionDistance());

        double sum = 0;
        ArrayList<Point[]> points = new ArrayList<>();
        for (Vector[] reference : references) {
            PointsDouble pd = viterbi.calc(reference, mus, sigmas);
            points.add(pd.getPoints());
            sum += pd.getDouble();

            //print matching
            Point[] p = pd.getPoints();
            /*System.out.println("Viterbi Punkte");
             for (int i = 0; i < p.length; i++) {
             System.out.println(p[i].x + " " + p[i].y);
             }*/
            Util.drawDiagram(p);
            System.out.println("Summe " + Util.r2d(pd.getDouble()) + "\n");

        }

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

        return new VectorsDoubleArray(mus, sigmas, sum, null);
    }
}
