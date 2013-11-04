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

    @Override
    public String toString() {
        String out = "(";
        for (int i = 0; i < size; i++) {
            out += Util.r2d(values[i]);
            if (i != size - 1) {
                out += ", ";
            }
        }
        out += ")";
        return out;
    }

    public static Vector add(Vector v1, Vector v2) throws RuntimeException {
        if (v1 == null && v2 == null) {
            return null;
        }
        if (v1 == null) {
            return v2;
        }
        if (v2 == null) {
            return v1;
        }
        if (v1.getSize() != v2.getSize()) {
            throw new RuntimeException("Vectors have different sizes");
        } else {
            double[] values1 = v1.getValues();
            double[] values2 = v2.getValues();

            double[] values = new double[values1.length];

            for (int i = 0; i < values1.length; i++) {
                values[i] = values1[i] + values2[i];
            }

            return new Vector(values);
        }
    }

    public void divide(int divisor) {
        if (divisor == 0) {
            return;
        }

        for (int i = 0; i < size; i++) {
            values[i] = values[i] / divisor;
        }
    }
}
