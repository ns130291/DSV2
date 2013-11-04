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
        int[] lengths = new int[references.size()];
        for (int i = 0; i < lengths.length; i++) {
            lengths[i] = references.get(i).length;
        }

        //lengths = new int[]{6, 7};
        //TODO: min length of array == 1
        Arrays.sort(lengths);
        int median;
        if (lengths.length % 2 == 1) {
            median = lengths[lengths.length / 2];
        } else {
            median = (lengths[lengths.length / 2] + lengths[lengths.length / 2 - 1]) / 2;
        }
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
            System.out.println("");
        }

        Vector[] model = new Vector[modelLength];
        int[] avg = new int[modelLength];
        for (int i = 0; i < references.size(); i++) {
            for (Point matching : points.get(i)) {
                avg[matching.y]++;
                model[matching.y] = Vector.add(model[matching.y], references.get(i)[matching.x]);
            }
        }

        System.out.println("Model");
        for (int i = 0; i < modelLength; i++) {
            model[i].divide(avg[i]);
            System.out.println(model[i] + "  Anzahl Komponenten " + avg[i]);
        }

        System.out.println("\nIterieren\n=============");
        
        double sum = Double.POSITIVE_INFINITY;
        double newSum = Double.MAX_VALUE;
        int i = 1;
        while(sum > newSum){
            sum = newSum;
            System.out.println("Schritt " + i);
            System.out.println("------------");
            i++;
            VectorsDouble vd = iterate(model, references);
            model = vd.getVectors();
            newSum = vd.getDouble();
            System.out.println("");
        }

    }

    private VectorsDouble iterate(Vector[] model, ArrayList<Vector[]> references) {

        Viterbi viterbi = new Viterbi(new EuclideanDistance2());

        double sum = 0;
        ArrayList<Point[]> points = new ArrayList<>();
        for (Vector[] reference : references) {
            PointsDouble pd = viterbi.calc(model, reference);
            points.add(pd.getPoints());
            sum += pd.getDouble();
            
            
            //print matching
            Point[] p = pd.getPoints();
            System.out.println("Viterbi Punkte");
            for(int i = 0; i < p.length; i++){
                System.out.println(p[i].x + " " + p[i].y);
            }
            Util.drawDiagram(p);
            System.out.println("Summe " + Util.r2d(pd.getDouble()) + "\n");
            
        }

        model = new Vector[model.length];
        int[] avg = new int[model.length];
        for (int i = 0; i < references.size(); i++) {
            for (Point matching : points.get(i)) {
                avg[matching.y]++;
                model[matching.y] = Vector.add(model[matching.y], references.get(i)[matching.x]);
            }
        }

        System.out.println("Model");
        for (int i = 0; i < model.length; i++) {
            model[i].divide(avg[i]);
            System.out.println(model[i] + "  Anzahl Komponenten " + avg[i]);
        }
        System.out.println("Sum " + Util.r2d(sum) + "\n");

        return new VectorsDouble(model, sum);
    }
}
