
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package containers;

/**
 * This class represents instances of {@link Container} that are specifically initialized as Liquid Containers with the
 * {@code "R"} token in the input. 
 * @author Eren Donmez
 *
 */
public class RefrigeratedContainer extends HeavyContainer {

	/**
	 * Constructor for {@code RefrigeratedContainer}.
	 * All instances of {@code RefrigeratedContaniner} have {@link Container#fuelMultiplier}{@code  == 5.00}.
	 * @param ID ID of the container. IDs of containers start from zero and are incremented by one with every instance.
	 * @param weight Weight of the container.
	 */
	public RefrigeratedContainer(int ID, int weight) {
		super(ID, weight);
		this.fuelMultiplier = 5.0;
	}

}



//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

