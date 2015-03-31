import java.util.Comparator;


public class CircuitComparator implements Comparator<Circuit>{

	@Override
	public int compare(Circuit c1, Circuit c2) {
		return c1.getFitnessScore() - c2.getFitnessScore();
	}

}
