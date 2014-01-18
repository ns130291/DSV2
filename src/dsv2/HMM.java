package dsv2;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author ns130291
 */
public class HMM {

    public Vector[] generate(int modelLength, long seed, Vector[] mus, Vector[] sigmas) {
        Random rand = new Random(seed);
        ArrayList<Vector> refs = new ArrayList<>();
        int zustand = 0;

        while (zustand < modelLength) {

            double[] mu = mus[zustand].getValues();
            double[] sigma = sigmas[zustand].getValues();

            double[] ref = new double[mu.length];

            for (int i = 0; i < mu.length; i++) {
                
                //TODO: vllt war das doch die richtige Richtung, nochmal ausprobieren
                //Umrechnung von negativen Zahlen klappt auch noch nicht ganz so glaub ich
                //ref[i] = (rand.nextGaussian() - mu[i]) / Math.sqrt(sigma[i]); --> Das war glaub die falsche Umrechnungsrichtung
                double n = rand.nextGaussian();
                if(n < 0){
                    n *= -1;
                }
                ref[i] = n * Math.sqrt(sigma[i]) + mu[i];
                //ref[i] = n * sigma[i] + mu[i];
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
        System.out.println("");
        
        return refs.toArray(new Vector[1]);
    }
}
