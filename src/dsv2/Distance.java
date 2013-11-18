package dsv2;

/**
 *
 * @author ns130291
 */
public interface Distance {
    public double calcDistance(Vector x, Vector mu, Vector sigma2) throws RuntimeException;
}
