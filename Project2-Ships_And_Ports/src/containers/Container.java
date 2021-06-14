
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package containers;

/**
 * This abstract class represent containers. Each {@code Container} has an {@code ID}, a {@code weight} determined by
 * the input, and a {@code fuelMultiplier} determined by the child class. 
 * 
 * @author Eren Donmez
 *
 */
public abstract class Container {
	
	/**
	 * ID of the {@code Container}. IDs of containers start from zero and are incremented by one with every instance.
	 */
	private final int ID;
	
	/**
	 * Weight of the container.
	 */
	private final int weight;
	
	/**
	 * The amount of fuel per kilometer that each kilogram of the container causes to be burnt.
	 * The values of {@code fuelMultiplier} for each {@code Container} class are as follows:
	 * <ul>
	 * <li>{@link BasicContainer}{@code : 2.50}
	 * <li>{@link HeavyContainer}{@code : 3.00}
	 * <li>{@link RefrigeratedContainer}{@code : 5.00}
	 * <li>{@link LiquidContainer}{@code : 4.00}
	 * </ul>
	 * The values for {@link RefrigeratedContainer} and {@link LiquidContainer} override the value for
	 * {@link HeavyContainer}.
	 */
	protected double fuelMultiplier;
	
	/**
	 * Constructor for {@code Container}.
	 * @param ID ID of the container. IDs of containers start from zero and are incremented by one with every instance.
	 * @param weight Weight of the container.
	 */
	public Container(int ID, int weight) {
		this.ID = ID;
		this.weight = weight;
	}
	
	/**
	 * Calculates the fuel consumption per kilometer of the container.
	 * @return Fuel consumption of the container per kilometer.
	 */
	public double consumption() {
		return this.weight * this.fuelMultiplier;
	}
	
	/**
	 * Checks if two containers are the same by comparing their respective {@link Container#ID}s.
	 * @param other The container that will be compared to the instance of the container that calls this method.
	 * @return {@code true} if the two containers are the same, {@code false} otherwise.
	 */
	public boolean equals(Container other) {
		return this.ID == other.ID && this.weight == other.weight && this.getClass() == other.getClass();
	}
	
	/**
	 * Getter method for {@link Container#weight}.
	 * @return Weight of the container.
	 */
	public int getWeight() {
		return this.weight;
	}
	
	/**
	 * Getter method for {@link Container#ID}.
	 * @return ID of the container.
	 */
	public int getID() {
		return this.ID;
	}
}



//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

