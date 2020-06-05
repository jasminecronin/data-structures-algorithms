import java.util.Comparator;

/**
 * Overrides the compare() method to tell the priority queue how Pair objects
 * should be ordered.
 * 
 * @author Jasmine Roebuck 30037334
 *
 */
public class PairComparator implements Comparator<Pair<Integer, Integer>> {

	@Override
	public int compare(Pair<Integer, Integer> P1, Pair<Integer, Integer> P2) {
		if (P1.getY() < P2.getY()) {
			return -1;
		}
		if (P1.getY() > P2.getY()) {
			return 1;
		}
		if (P1.getX() < P2.getX()) {
			return -1;
		}
		if (P1.getX() > P2.getX()) {
			return 1;
		}
		return 0;
	}

}
