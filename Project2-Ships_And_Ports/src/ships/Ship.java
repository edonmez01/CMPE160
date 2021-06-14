
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package ships;

import java.util.ArrayList;
import java.util.Collections;

import ports.Port;
import containers.*;
import interfaces.IShip;

/**
 * This class represents ships that have the abilities to:
 * <ul>
 * <li> Travel between ports
 * <li> Load and unload containers at ports
 * </ul>
 * Each ship has certain limits to the amount of containers it can carry. These limits are specified by the input.
 * @author Eren Donmez
 *
 */
public class Ship implements IShip {
	
	/**
	 * ID of the {@code Ship}. IDs of ships start from zero and are incremented by one with every instance.
	 */
	private final int ID;
	
	/**
	 * Current amount of fuel on the ship. The amount of fuel determines if the ship will be able to travel from one
	 * port to another.
	 */
	private double fuel;
	
	/**
	 * The port that the ship is currently at.
	 */
	private Port currentPort;
	
	/**
	 * The total weight capacity of the ship. A container cannot be loaded on the ship if its total weight will exceed
	 * {@code weightCapacity}.
	 */
	private final int weightCapacity;
	
	/**
	 * The maximum number of containers that the ship is able to carry.
	 */
	private final int maxContainers;
	
	/**
	 * The maximum number of heavy containers that the ship is able to carry (including refrigerated and liquid
	 * containers).
	 */
	private final int maxHeavyContainers;
	
	/**
	 * The maximum number of refrigerated containers that the ship is able to carry.
	 */
	private final int maxFridgeContainers;
	
	/**
	 * The maximum number of liquid containers that the ship is able to carry.
	 */
	private final int maxLiquidContainers;
	
	/**
	 * The fuel that the ship itself burns per kilometer. The extra fuel cost stemming from the containers in the ship
	 * is not included in this field.
	 */
	private final double fuelPerKm;
	
	/**
	 * List of the containers that are currently in the ship.
	 */
	private ArrayList<Container> currentContainers;
	
	/**
	 * Sum of the weights of all the containers that are currently in the ship.
	 */
	private int currentWeight;
	
	/**
	 * Number of containers that are currently inside the ship. This is equivalent to {@code currentContainers.size()}.
	 */
	private int numOfContainers;
	
	/**
	 * Number of heavy containers that are currently inside the ship.
	 */
	private int numOfHeavyContainers;
	
	/**
	 * Number of refrigerated containers that are currently inside the ship.
	 */
	private int numOfFridgeContainers;
	
	/**
	 * Number of liquid containers that are currently inside the ship.
	 */
	private int numOfLiquidContainers;
	
	/**
	 * Constructor for the {@code Ship} class. IDs of ships start from zero and are incremented by one with every 
	 * instance, whereas all other parameters are specified in the input.
	 * @param ID ID of the ship.
	 * @param p Initial port that the ship is created in.
	 * @param totalWeightCapacity Maximum weight that the ship can carry.
	 * @param maxNumberOfAllContainers Maximum number of containers that the ship can carry.
	 * @param maxNumberOfHeavyContainers Maximum number of heavy containers that the ship can carry.
	 * @param maxNumberOfRefrigeratedContainers Maximum number of refrigerated containers that the ship can carry.
	 * @param maxNumberOfLiquidContainers Maximum number of liquid containers that the ship can carry.
	 * @param fuelConsumptionPerKM Base fuel consumption per kilometer of the ship.
	 */
	public Ship(int ID, Port p, int totalWeightCapacity, int maxNumberOfAllContainers, int maxNumberOfHeavyContainers,
			int maxNumberOfRefrigeratedContainers, int maxNumberOfLiquidContainers, double fuelConsumptionPerKM) {
		this.ID = ID;
		this.currentPort = p;
		this.fuel = 0.0;
		this.weightCapacity = totalWeightCapacity;
		this.maxContainers = maxNumberOfAllContainers;
		this.maxHeavyContainers = maxNumberOfHeavyContainers;
		this.maxFridgeContainers = maxNumberOfRefrigeratedContainers;
		this.maxLiquidContainers = maxNumberOfLiquidContainers;
		this.fuelPerKm = fuelConsumptionPerKM;
		this.currentContainers = new ArrayList<Container>();
		this.currentWeight = 0;
		this.numOfContainers = 0;
		this.numOfHeavyContainers = 0;
		this.numOfFridgeContainers = 0;
		this.numOfLiquidContainers = 0;
		
		p.incomingShip(this);
	}
	
	/**
	 * Getter method for {@link Ship#currentContainers}.
	 * @return List of all the containers that are currently in the ship.
	 */
	public ArrayList<Container> getCurrentContainers(){
		this.currentContainers.sort(new ContainerComparator());
		return this.currentContainers;
	}
	
	/**
	 * Calculates the fuel cost of traveling from the ship's current port to another specified port. The formula is as
	 * follows: {@code [sum of all the containers' fuel costs per kilometer + this.fuelPerKm] * distance to the other
	 * port}.
	 * @param destination The destination for calculating the fuel cost.
	 * @return The calculated fuel cost.
	 */
	private double calculateFuelCost(Port destination) {
		double containerCostPerKm = 0.0;
		for(Container c : this.currentContainers) {
			containerCostPerKm += c.consumption();
		}
		return (containerCostPerKm + this.fuelPerKm) * this.currentPort.getDistance(destination);
	}
	
	/**
	 * Simulates the ship sailing from its current port to another specified port. First, it is checked if the ship has
	 * enough fuel to travel to its destination. If it has sufficient fuel, the amount of fuel needed for this
	 * trip is reduced from the ship's fuel amount. Then the ship is removed from its current port and added to the
	 * destination port.
	 * @param p The destination port.
	 * @return {@code true} if the ship has successfully sailed to its destination, {@code false} otherwise.
	 */
	@Override
	public boolean sailTo(Port p) {
		double totalCost = this.calculateFuelCost(p);
		if(this.fuel < totalCost) {
			return false;
		} else {
			this.fuel -= totalCost;
			this.currentPort.outgoingShip(this);
			p.incomingShip(this);
			this.currentPort = p;
			return true;
		}
	}
	
	/**
	 * Adds a specified amount of fuel to the ship's current fuel.
	 * @param newFuel The amount of fuel to be added.
	 */
	@Override
	public void reFuel(double newFuel) {
		this.fuel += newFuel;
	}
	
	/**
	 * Loads a specified container to the ship. This operation fails if:
	 * <ul>
	 * <li> The specified container is already inside the ship
	 * <li> The specified container is not at the same port as the ship
	 * <li> The weight or container count limits of the ship do not allow the specified container to be loaded
	 * If the container can be loaded to the ship, the container is removed from the port and added to the ship.
	 * @param cont The container to be loaded to the ship.
	 * @return {@code true} if the container has successfully been loaded to the ship, {@code false} otherwise.
	 */
	@Override
	public boolean load(Container cont) {
		if(this.currentContainers.contains(cont) || !this.currentPort.contains(cont)) {
			return false;
		} else if(this.weightCapacity < this.currentWeight + cont.getWeight()) {
			return false;
		} else if(this.numOfContainers == this.maxContainers) {
			return false;
		} else if(cont instanceof HeavyContainer && this.numOfHeavyContainers == this.maxHeavyContainers) {
			return false;
		} else if(cont instanceof RefrigeratedContainer && this.numOfFridgeContainers == this.maxFridgeContainers) {
			return false;
		} else if(cont instanceof LiquidContainer && this.numOfLiquidContainers == this.maxLiquidContainers) {
			return false;
		} else {
			this.currentContainers.add(cont);
			this.currentWeight += cont.getWeight();
			this.numOfContainers++;
			if(cont instanceof HeavyContainer) {
				this.numOfHeavyContainers++;
			}
			if(cont instanceof RefrigeratedContainer) {
				this.numOfFridgeContainers++;
			}
			if(cont instanceof LiquidContainer) {
				this.numOfLiquidContainers++;
			}
			this.currentPort.removeContainer(cont);
			return true;
		}
	}
	
	/**
	 * Unloads a specified container from the ship. This operation fails if the specified container isn't currently
	 * in the ship.
	 * @param cont The container to be unloaded from the ship.
	 * @return {@code true} if the container has successfully been unloaded from the ship, {@code false} otherwise.
	 */
	@Override
	public boolean unLoad(Container cont) {
		if(!this.currentContainers.contains(cont)) {
			return false;
		} else {
			this.currentContainers.remove(cont);
			this.currentWeight -= cont.getWeight();
			this.numOfContainers--;
			if(cont instanceof HeavyContainer) {
				this.numOfHeavyContainers--;
			}
			if(cont instanceof RefrigeratedContainer) {
				this.numOfFridgeContainers--;
			}
			if(cont instanceof LiquidContainer) {
				this.numOfLiquidContainers--;
			}
			this.currentPort.addContainer(cont);
			return true;
		}
	}
	
	/**
	 * Getter method for {@link Ship#ID}.
	 * @return ID of the ship.
	 */
	public int getID() {
		return this.ID;
	}
	
	/**
	 * Getter method for {@link Ship#fuel}.
	 * @return The current amount of fuel on the ship.
	 */
	public double getFuel() {
		return this.fuel;
	}
	
	/**
	 * This method creates a list of 4 lists for each container class: {@link BasicContainer}, {@link HeavyContainer},
	 * {@link RefrigeratedContainer}, and {@link LiquidContainer}. Each of these lists is then populated with the
	 * IDs of current containers in the ship, with the appropriate type. The lists are then sorted by ascending ID.
	 * Then, each ID is converted to a string and the list of lists of strings is then returned to be used solely for
	 * outputting purposes.
	 * @return A list of lists of strings which contains all the IDs of containers that are currently at the ship.
	 */
	public ArrayList<ArrayList<String>> getContainersStr() {  // basic, heavy, fridge, liquid
		ArrayList<ArrayList<Integer>> out = new ArrayList<ArrayList<Integer>>();
		for(int i = 0; i < 4; i++) {
			out.add(new ArrayList<Integer>());
		}
		for(Container container : this.currentContainers) {
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
}



//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

