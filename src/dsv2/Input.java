package dsv2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ns130291
 */
public class Input {
    public ArrayList<Vector[]> fromFile(String path){
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path));
            
            String temp = br.readLine();
            int vectorSize = Integer.parseInt(temp);
            
            temp = br.readLine();
            int numReferences = Integer.parseInt(temp);
            
            br.readLine();
            br.readLine();
            
            ArrayList<Vector[]> references = new ArrayList<>();
            for(int i = 0; i < numReferences; i++){
                temp = br.readLine();
                if(temp != null){
                    String[] vector = temp.split("    ");
                    Vector[] x = new Vector[vector.length];
                    for(int j = 0; j < vector.length; j++){
                        String[] values = vector[j].split(" ");
                        x[j] = new Vector(stringArrayToDoubleArray(values));
                    }
                    references.add(x);
                }
            }
            return references;
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Input.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Input.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(Input.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    private double[] stringArrayToDoubleArray(String[] array){
        double[] doubles = new double[array.length];
        for(int i = 0; i < array.length; i++){
            doubles[i] = Double.parseDouble(array[i]);
        }
        return doubles;
    }
}
