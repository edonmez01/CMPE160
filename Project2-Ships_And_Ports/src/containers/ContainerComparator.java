package containers;

import java.util.Comparator;

/**
 * This comparator sorts instances of {@link Container} by id.
 * @author Eren
 *
 */
public class ContainerComparator implements Comparator<Container>{
	@Override
	public int compare(Container c1, Container c2) {
		Integer id1 = c1.getID();
		Integer id2 = c2.getID();
		return id1.compareTo(id2);
	}
}
