
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package ports;

import java.util.ArrayList;
import java.util.Collections;
import java.lang.Math;

import containers.*;
import ships.Ship;
import interfaces.IPort;

/**
 * This class represents ports where ships and containers are initialized and the loading/unloading of the ships
 * are performed.
 * @author Eren Donmez
 *
 */
public class Port implements IPort {
	
	/**
	 * ID of the {@code Port}. IDs of ports start from zero and are incremented by one with every instance.
	 */
	private final int ID;
	
	/**
	 * Cartesian x-coordinate of the port.
	 */
	private final double X;
	
	/**
	 * Cartesian y-coordinate of the port.
	 */
	private final double Y;
	
	/**
	 * List of containers that are currently at the port, but not inside a ship.
	 */
	private ArrayList<Container> containers;
	
	/**
	 * List of ships that have visited and left the port. Even if a ship visits the port multiple times, it is added
	 * to this list only once. Therefore, no element can appear more than once in the list.
	 */
	private ArrayList<Ship> history;
	
	/**
	 * List of ships that are currently at the port. When a ship leaves the port, it is deleted from this list and
	 * added to the {@link Port#history} list (if it is not already in that list). Therefore, no element can appear
	 * more than once in this list.
	 */
	private ArrayList<Ship> current;
	
	/**
	 * Constructor for the {@code Port} class. IDs of ports start from zero and are incremented by one with 
	 * every instance. The x- and y- coordinates of ports are specified in the input.
	 * @param ID ID of the port.
	 * @param X Cartesian x-coordinate of the port.
	 * @param Y Cartesian y-coordinate of the port.
	 */
	public Port(int ID, double X, double Y) {
		this.ID = ID;
		this.X = X;
		this.Y = Y;
		this.containers = new ArrayList<Container>();
		this.history = new ArrayList<Ship>();
		this.current = new ArrayList<Ship>();
	}
	
	/**
	 * Calculates the Euclidean distance between two ports.
	 * @param other The port whose distance to the current port will be calculated.
	 * @return The Euclidean distance between the two ports.
	 */
	public double getDistance(Port other) {
		double distance = Math.sqrt(Math.pow(this.X - other.X, 2) + Math.pow(this.Y - other.Y, 2));
		return distance;
	}
	
	/**
	 * Checks if a container is currently at the port.
	 * @param c The container whose existence at the current port will be checked.
	 * @return {@code true} if {@code c} is currently at the port, {@code false} otherwise.
	 */
	public boolean contains(Container c) {
		return this.containers.contains(c);
	}
	
	/**
	 * Adds a container to the port. This container is either newly created or it is unloaded from a ship.
	 * @param c The container that will be added to the port.
	 */
	public void addContainer(Container c) {
		this.containers.add(c);
	}
	
	/**
	 * Removes a container from the port. This container should then be loaded to a ship.
	 * @param c The container that will be removed from the port.
	 */
	public void removeContainer(Container c) {
		this.containers.remove(c);
	}
	
	/**
	 * Adds a ship to the current port. This ship is either newly created of it has traveled to the current
	 * port from another port.
	 * @param s The ship that will be added to the port.
	 */
	@Override
	public void incomingShip(Ship s) {
		if(!(this.current.contains(s))){
			this.current.add(s);
		}
	}
	
	/**
	 * Removes a ship from the current port. This ship should then travel to another port.
	 * @param s The ship that will be removed from the port.
	 */
	@Override
	public void outgoingShip(Ship s) {
		this.current.remove(s);
		if(!this.history.contains(s)) {
			this.history.add(s);
		}
		
	}
	
	/**
	 * This method creates a formatted string representing the cartesian coordinates of the port, solely for outputting
	 * purposes. This string has the format: {@code "[}{@link Port#X}{@code ], [}{@link Port#Y}{@code ]"}. 
	 * {@link Port#X} and {@link Port#Y} are rounded to 2 decimal digits.
	 * @return The formatted string.
	 */
	public String getLocationStr() {
		return String.format("(%.2f, %.2f)", this.X, this.Y);
	}
	
	/**
	 * Getter method for {@link Port#ID}.
	 * @return ID of the port.
	 */
	public int getID() {
		return this.ID;
	}
	
	/**
	 * This method creates a list of 4 lists for each container class: {@link BasicContainer}, {@link HeavyContainer},
	 * {@link RefrigeratedContainer}, and {@link LiquidContainer}. Each of these lists is then populated with the
	 * IDs of current containers in the port, with the appropriate type. The lists are then sorted by ascending ID.
	 * Then, each ID is converted to a string and the list of lists of strings is then returned to be used solely for
	 * outputting purposes.
	 * @return A list of lists of strings which contains all the IDs of containers that are currently at the port.
	 */
	public ArrayList<ArrayList<String>> getContainersStr() {  // basic, heavy, fridge, liquid
		ArrayList<ArrayList<Integer>> out = new ArrayList<ArrayList<Integer>>();
		for(int i = 0; i < 4; i++) {
			out.add(new ArrayList<Integer>());
		}
		for(Container container : this.containers) {
			if(container instanceof BasicContainer) {
				out.get(0).add(container.getID());
			} else if(container instanceof RefrigeratedContainer) {
				out.get(2).add(container.getID());
			} else if(container instanceof LiquidContainer) {
				out.get(3).add(container.getID());
			} else if(container instanceof HeavyContainer) {
				out.get(1).add(container.getID());
			}
		}
		
		for(int i = 0; i < 4; i++) {
			Collections.sort(out.get(i));
		}
		
		ArrayList<ArrayList<String>> outStr = new ArrayList<ArrayList<String>>();
		for(int i = 0; i < 4; i++) {
			outStr.add(new ArrayList<String>());
		}
		for(int i = 0; i < 4; i++) {
			for(Integer e : out.get(i)) {
				outStr.get(i).add(e.toString());
			}
		}
		
		return outStr;
	}
	
	/**
	 * This method creates a sorted list of the IDs of all the ships that are currently at the port.
	 * @return The sorted list of the IDs of all the ships that are currently at the port.
	 */
	public ArrayList<Integer> getShipIDs() {
		ArrayList<Integer> out = new ArrayList<Integer>();
		for(Ship ship : this.current) {
			out.add(ship.getID());
		}
		Collections.sort(out);
		return out;
	}
}



//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

