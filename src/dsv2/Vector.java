package dsv2;

/**
 *
 * @author ns130291
 */
public class Vector {

    private double values[];
    private int size;

    public Vector(int size) {
        this.size = size;
    }

    public Vector(double[] values) {
        this.size = values.length;
        this.values = values;
    }

    public double[] getValues() {
        return values;
    }

    public void setValues(double[] values) throws Exception {
        if (size == values.length) {
            this.values = values;
        } else {
            throw new Exception("Wrong array length");
        }
    }

    public int getSize() {
        return size;
    }
    
}
