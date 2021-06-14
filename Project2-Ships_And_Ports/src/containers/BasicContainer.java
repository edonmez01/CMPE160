
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package containers;

/**
 * This class represents instances of {@link Container} that have {@code weight <= 3000}. 
 * 
 * @author Eren Donmez
 * 
 */
public class BasicContainer extends Container {
	
	/**
	 * Constructor for {@code BasicContainer}.
	 * All instances of {@code BasicContaniner} have {@link Container#fuelMultiplier}{@code  == 2.50}.
	 * @param ID ID of the container. IDs of containers start from zero and are incremented by one with every instance.
	 * @param weight Weight of the container.
	 */
	public BasicContainer(int ID, int weight) {
		super(ID, weight);
		this.fuelMultiplier = 2.5;
	}

}


//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

