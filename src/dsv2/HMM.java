package dsv2;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author ns130291
 */
public class HMM {

    public void generate() {
        int modelLength = 7;
        long seed = 42;
        Random rand = new Random();
        ArrayList<Vector> refs = new ArrayList<>();
        int zustand = 0;

        Vector[] mus = {
            new Vector(new double[]{2, 5, 3}),
            new Vector(new double[]{1, 2, 5}),
            new Vector(new double[]{4, 2, 3}),
            new Vector(new double[]{1, 4, 3}),
            new Vector(new double[]{6, 10, 10}),
            new Vector(new double[]{8, 2, 3}),
            new Vector(new double[]{8, 9, 3}),};

        Vector[] sigmas = {
            new Vector(new double[]{3, 10, 5}),
            new Vector(new double[]{11, 2, 3}),
            new Vector(new double[]{1, 1, 4}),
            new Vector(new double[]{9, 2, 1}),
            new Vector(new double[]{2, 1, 3}),
            new Vector(new double[]{8, 1, 4}),
            new Vector(new double[]{3, 3, 3}),};

        while (zustand < modelLength) {

            double[] mu = mus[zustand].getValues();
            double[] sigma = sigmas[zustand].getValues();

            double[] ref = new double[mu.length];

            for (int i = 0; i < mu.length; i++) {
                //ref[i] = (rand.nextGaussian() - mu[i]) / Math.sqrt(sigma[i]); --> Das war glaub die falsche Umrechnungsrichtung
                double n = rand.nextGaussian();
                if(n < 0){
                    n *= -1;
                }
                ref[i] = n * Math.sqrt(sigma[i]) + mu[i];
            }

            refs.add(new Vector(ref));

            if (zustand < modelLength - 2) {
                if (rand.nextDouble() <= (1.0 / 3.0)) {
                    zustand++;
                } else if (rand.nextDouble() <= (2.0 / 3.0)) {
                    zustand += 2;
                }
            } else {
                if (rand.nextDouble() <= 0.5) {
                    zustand++;
                }
            }
        }
        
        for(Vector x:refs){
            System.out.println(x);
        }
    }
}
