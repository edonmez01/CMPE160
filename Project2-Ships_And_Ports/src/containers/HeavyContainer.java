
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package containers;

/**
 * This class represents instances of {@link Container} that have {@code weight <= 3000} and are not Liquid or 
 * Refrigerated containers.
 * @author Eren Donmez
 *
 */
public class HeavyContainer extends Container {

	/**
	 * Constructor for {@code HeavyContainer}.
	 * All instances of {@code HeavyContaniner} have {@link Container#fuelMultiplier}{@code  == 3.00}.
	 * @param ID ID of the container. IDs of containers start from zero and are incremented by one with every instance.
	 * @param weight Weight of the container.
	 */
	public HeavyContainer(int ID, int weight) {
		super(ID, weight);
		this.fuelMultiplier = 3.0;
	}
	
}



//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

