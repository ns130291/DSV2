/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsv2;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author ns130291
 */
public class DSV2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*Vector eins = new Vector(new double[]{4, 1, -2});
         Vector zwei = new Vector(new double[]{2, 3, -1});
        
         Distance d = new EuclideanDistance2();
         double dis = -1;
            
         dis = d.calcDistance(eins, zwei, null);
        
         //System.out.println("Distanz: " + dis); 
         System.out.println("");
        
        
         Vector[] a = new Vector[]{new Vector(new double[]{9, 0 ,1}), new Vector(new double[]{6, 2, 1}), new Vector(new double[]{4, 2, 1}), new Vector(new double[]{1, 0 ,2})};
         Vector[] x = new Vector[]{new Vector(new double[]{9, 1, 1}), new Vector(new double[]{8, 1, 0}), new Vector(new double[]{4, 2, 1}), new Vector(new double[]{4, 3, 1}), new Vector(new double[]{3, 2, 0}), new Vector(new double[]{2, 0, 1})};
        
         Viterbi viterbi = new Viterbi(new EuclideanDistance2());
         PointsDouble matching = viterbi.calc(x, a, null);
        
         /*System.out.println("Zuordnung");
         for(Point p1:matching.getPoints()){
         System.out.println(p1.x + " " + p1.y);
         }
         System.out.println("");*/

        //Aufgabe 2
        /*System.out.println("Aufgabe 2");
        
         String path = "C:\\Users\\ns130291\\Desktop\\Java\\DSV2\\daten.txt";
        
         Input i = new Input();
         ArrayList<Vector[]> references =  i.fromFile(path);
        
         for(Vector[] xx:references){
         for(Vector xxx:xx){
         System.out.println(xxx);
         }
         System.out.println("");
         }
        
         ViterbiTraining viterbiTraining = new ViterbiTraining();
         viterbiTraining.train(references);*/
        /*System.out.println("Aufgabe 4");
        
         String path2 = "C:\\Users\\ns130291\\Desktop\\Java\\DSV2\\daten2.txt";
         Input i2 = new Input();
         ArrayList<Vector[]> references2 =  i2.fromFile(path2);
        
         for(Vector[] xx:references2){
         for(Vector xxx:xx){
         System.out.println(xxx);
         }
         System.out.println("");
         }
         ViterbiTrainingHMM viterbiTraining2 = new ViterbiTrainingHMM();
         viterbiTraining2.train(references2, 4);*/
        Random rand = new Random(42);
        HMM hmm = new HMM();
        int modelLength = 7;

        Vector[] mus1 = {
            new Vector(new double[]{2, 5, 3}),
            new Vector(new double[]{1, 2, 5}),
            new Vector(new double[]{4, 2, 3}),
            new Vector(new double[]{1, 4, 3}),
            new Vector(new double[]{6, 10, 10}),
            new Vector(new double[]{8, 2, 3}),
            new Vector(new double[]{8, 9, 3}),};

        Vector[] sigmas1 = {
            new Vector(new double[]{3, 10, 5}),
            new Vector(new double[]{11, 2, 3}),
            new Vector(new double[]{1, 1, 4}),
            new Vector(new double[]{9, 2, 1}),
            new Vector(new double[]{2, 1, 3}),
            new Vector(new double[]{8, 1, 4}),
            new Vector(new double[]{3, 3, 3}),};
/*
        // 100 Merkmalvektorfolgen für HMM1 generieren
        ArrayList<Vector[]> refs1 = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            refs1.add(hmm.generate(modelLength, rand.nextLong(), mus1, sigmas1));
        }
        // 100 Merkmalvektorfolgen für HMM2 generieren
        ArrayList<Vector[]> refs2 = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            refs2.add(hmm.generate(modelLength, rand.nextLong(), sigmas1, mus1));
        }

        System.out.println("refs1 size " + refs1.size());
        System.out.println("refs2 size " + refs2.size());*/

        // Aus Merkmalvektorfolgen trainieren
        ViterbiTrainingHMM viterbiTrainingHMM = new ViterbiTrainingHMM();/*
        VectorsDoubleArray hmm1 = viterbiTrainingHMM.train(refs1, modelLength);
        VectorsDoubleArray hmm2 = viterbiTrainingHMM.train(refs2, modelLength);*/

        // 100 Merkmalvektorfolgen von HMM1 generieren zum Testen der trainierten HMMs
        ArrayList<Vector[]> refs11 = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            refs11.add(hmm.generate(modelLength, rand.nextLong(), mus1, sigmas1));
        }
        // 100 Merkmalvektorfolgen von HMM2 generieren zum Testen der trainierten HMMs
        ArrayList<Vector[]> refs22 = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            refs22.add(hmm.generate(modelLength, rand.nextLong(), sigmas1, mus1));
        }

        ViterbiHMM viterbiHMM = new ViterbiHMM(new LogarithmisedNormalDistributionDistance());
        /*
        //Klassifizieren der neuen 100 Merkmalvektorfolgen von HMM1
        ArrayList<PointsDouble> resultHMM1refs11 = new ArrayList<>();
        ArrayList<PointsDouble> resultHMM2refs11 = new ArrayList<>();
        for (Vector[] x : refs11) {
            PointsDouble result1 = viterbiHMM.calc(x, hmm1.getVectors1(), hmm1.getVectors2(), hmm1.getArray());
            PointsDouble result2 = viterbiHMM.calc(x, hmm2.getVectors1(), hmm2.getVectors2(), hmm2.getArray());
            System.out.print("HMM1 " + Util.r2d(result1.getDouble()) + " HMM2 " + Util.r2d(result2.getDouble()));
            if (result1.getDouble() < result2.getDouble()) {
                System.out.println(" r = 1");
            } else {
                System.out.println(" r = 2");
            }
            resultHMM1refs11.add(result1);
            resultHMM2refs11.add(result2);
        }
        
        System.out.println("");
        System.out.println("");
        System.out.println("");
        
        //Klassifizieren der neuen 100 Merkmalvektorfolgen von HMM2
        ArrayList<PointsDouble> resultHMM1refs22 = new ArrayList<>();
        ArrayList<PointsDouble> resultHMM2refs22 = new ArrayList<>();
        for (Vector[] x : refs22) {
            PointsDouble result1 = viterbiHMM.calc(x, hmm1.getVectors1(), hmm1.getVectors2(), hmm1.getArray());
            PointsDouble result2 = viterbiHMM.calc(x, hmm2.getVectors1(), hmm2.getVectors2(), hmm2.getArray());
            System.out.print("HMM1 " + Util.r2d(result1.getDouble()) + " HMM2 " + Util.r2d(result2.getDouble()));
            if (result1.getDouble() < result2.getDouble()) {
                System.out.println(" r = 1");
            } else {
                System.out.println(" r = 2");
            }
            resultHMM1refs22.add(result1);
            resultHMM2refs22.add(result2);
        }

        System.out.println("");
        System.out.println("");
        System.out.println("");*/
        
        // 3 Merkmalvektorfolgen für HMM1 generieren
        ArrayList<Vector[]> refs111 = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            refs111.add(hmm.generate(modelLength, rand.nextLong(), mus1, sigmas1));
        }
        // 3 Merkmalvektorfolgen für HMM2 generieren
        ArrayList<Vector[]> refs222 = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            refs222.add(hmm.generate(modelLength, rand.nextLong(), sigmas1, mus1));
        }
        
        // Aus Merkmalvektorfolgen trainieren
        VectorsDoubleArray hmm11 = viterbiTrainingHMM.train(refs111, modelLength);
        VectorsDoubleArray hmm22 = viterbiTrainingHMM.train(refs222, modelLength);
        
        //Klassifizieren der neuen 100 Merkmalvektorfolgen von HMM11
        ArrayList<PointsDouble> resultHMM1refs11_ = new ArrayList<>();
        ArrayList<PointsDouble> resultHMM2refs11_ = new ArrayList<>();
        for (Vector[] x : refs11) {
            PointsDouble result1 = viterbiHMM.calc(x, hmm11.getVectors1(), hmm11.getVectors2(), hmm11.getArray());
            PointsDouble result2 = viterbiHMM.calc(x, hmm22.getVectors1(), hmm22.getVectors2(), hmm22.getArray());
            System.out.print("HMM11 " + Util.r2d(result1.getDouble()) + " HMM22 " + Util.r2d(result2.getDouble()));
            if (result1.getDouble() < result2.getDouble()) {
                System.out.println(" r = 1");
            } else {
                System.out.println(" r = 2");
            }
            resultHMM1refs11_.add(result1);
            resultHMM2refs11_.add(result2);
        }
        
        System.out.println("");
        System.out.println("");
        System.out.println("");
        
        //Klassifizieren der neuen 100 Merkmalvektorfolgen von HMM22
        ArrayList<PointsDouble> resultHMM1refs22_ = new ArrayList<>();
        ArrayList<PointsDouble> resultHMM2refs22_ = new ArrayList<>();
        for (Vector[] x : refs22) {
            PointsDouble result1 = viterbiHMM.calc(x, hmm11.getVectors1(), hmm11.getVectors2(), hmm11.getArray());
            PointsDouble result2 = viterbiHMM.calc(x, hmm22.getVectors1(), hmm22.getVectors2(), hmm22.getArray());
            System.out.print("HMM11 " + Util.r2d(result1.getDouble()) + " HMM22 " + Util.r2d(result2.getDouble()));
            if (result1.getDouble() < result2.getDouble()) {
                System.out.println(" r = 1");
            } else {
                System.out.println(" r = 2");
            }
            resultHMM1refs22_.add(result1);
            resultHMM2refs22_.add(result2);
        }
        
        //Util.muSigma(new double[]{2, 6, 1, 5, 7});
    }

}
