
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import java.util.Scanner;
import java.util.ArrayList;
import containers.*;
import ports.Port;
import ships.Ship;

/**
 * This is the second project of the CMPE160 Spring 2021 course. It can be described as a basic simulation of a network
 * of ports and ships traveling between them. The Main class handles the inputs and outputs of the project.
 * 
 * @author Eren Donmez
 * 
 */
public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		
		//
		// Main receives two arguments: path to input file and path to output file.
		// You can assume that they will always be provided, so no need to check them.
		// Scanner and PrintStream are already defined for you.
		// Use them to read input and write output.
		// 
		// Good Luck!
		// 
		
		Scanner in = new Scanner(new File(args[0]));
		PrintStream out = new PrintStream(new File(args[1]));
		
		ArrayList<Container> containers = new ArrayList<Container>();
		ArrayList<Ship> ships = new ArrayList<Ship>();
		ArrayList<Port> ports = new ArrayList<Port>();
		
		int N = Integer.parseInt(in.nextLine());
		for(int i = 0; i < N; i++) {
			String line = in.nextLine();
			String[] tokens = line.split(" ");
			String token_id = tokens[0];
			
			if(token_id.equals("1")) {       // Creating a container
				// int portID, int weight [, String type]
				// weight <= 3000 ? BasicContainer : HeavyContainer
				// type can only be R for Refrigerated or L for Liquid
				// container ID: starts from 0, incremented by 1 with each Container instance
				int portID = Integer.parseInt(tokens[1]);
				int weight = Integer.parseInt(tokens[2]);
				String type = "";
				if(tokens.length == 4) {
					type = tokens[3];
				}
				
				Container container;
				if(type.equals("R")) {
					container = new RefrigeratedContainer(containers.size(), weight);
				} else if(type.equals("L")) {
					container = new LiquidContainer(containers.size(), weight);
				} else if(weight > 3000) {
					container = new HeavyContainer(containers.size(), weight);
				} else {
					container = new BasicContainer(containers.size(), weight);
				}
				containers.add(container);
				ports.get(portID).addContainer(container);
				
			}
			else if(token_id.equals("2")) {  // Creating a ship
				// int portID, int weightCapacity > 0, int maxContainers, int maxHeavyContainers, int maxFridgeContainers, int maxLiquidContainers, double fuelPerKm
				int portID = Integer.parseInt(tokens[1]);
				int weightCapacity = Integer.parseInt(tokens[2]);
				int maxContainers = Integer.parseInt(tokens[3]);
				int maxHeavyContainers = Integer.parseInt(tokens[4]);
				int maxFridgeContainers = Integer.parseInt(tokens[5]);
				int maxLiquidContainers = Integer.parseInt(tokens[6]);
				double fuelPerKm = Double.parseDouble(tokens[7]);
				
				Ship ship = new Ship(ships.size(), ports.get(portID), weightCapacity, maxContainers, maxHeavyContainers, maxFridgeContainers, maxLiquidContainers, fuelPerKm);
				ships.add(ship);
			}
			else if(token_id.equals("3")) {  // Creating a port
				// double xCoordinate, double yCoordinate
				// port ID: starts from 0, incremented by 1 with each Container instance
				double xCoordinate = Double.parseDouble(tokens[1]);
				double yCoordinate = Double.parseDouble(tokens[2]);
				Port port = new Port(ports.size(), xCoordinate, yCoordinate);
				ports.add(port);
				
			}
			else if(token_id.equals("4")) {  // Loading a container to a ship
				// int shipID, int containerID
				// ship and container should be at the same port
				// capacity of the ship should be paid attention to
				int shipID = Integer.parseInt(tokens[1]);
				int containerID = Integer.parseInt(tokens[2]);
				ships.get(shipID).load(containers.get(containerID));
				
			}
			else if(token_id.equals("5")) {  // Unloading a container from a ship
				// int shipID, int containerID
				// the container may not be in the ship
				int shipID = Integer.parseInt(tokens[1]);
				int containerID = Integer.parseInt(tokens[2]);
				ships.get(shipID).unLoad(containers.get(containerID));
				
			}
			else if(token_id.equals("6")) {  // Ship sails to another port
				// int shipID, int destinationPortID
				// ship will sail if it has enough fuel
				int shipID = Integer.parseInt(tokens[1]);
				int destinationPortID = Integer.parseInt(tokens[2]);
				ships.get(shipID).sailTo(ports.get(destinationPortID));
				
			}
			else if(token_id.equals("7")) {  // Ship is refueled
				// int shipID, double fuelAmount > 0
				int shipID = Integer.parseInt(tokens[1]);
				double fuelAmount = Double.parseDouble(tokens[2]);
				ships.get(shipID).reFuel(fuelAmount);
				
			}
		}
		
		for(Port port : ports) {
			out.println("Port " + port.getID() + ": " + port.getLocationStr());
			ArrayList<ArrayList<String>> portContainers = port.getContainersStr();
			if(portContainers.get(0).size() > 0) {
				out.println("  BasicContainer: " + String.join(" ", portContainers.get(0)));
			}
			if(portContainers.get(1).size() > 0) {
				out.println("  HeavyContainer: " + String.join(" ", portContainers.get(1)));
			}
			if(portContainers.get(2).size() > 0) {
				out.println("  RefrigeratedContainer: " + String.join(" ", portContainers.get(2)));
			}
			if(portContainers.get(3).size() > 0) {
				out.println("  LiquidContainer: " + String.join(" ", portContainers.get(3)));
			}
			for(Integer shipID : port.getShipIDs()) {
				Ship ship = ships.get(shipID);
				double fuel = ship.getFuel();
				out.println("  Ship " + shipID + ": " + String.format("%.2f", fuel));
				ArrayList<ArrayList<String>> shipContainers = ship.getContainersStr();
				if(shipContainers.get(0).size() > 0) {
					out.println("    BasicContainer: " + String.join(" ", shipContainers.get(0)));
				}
				if(shipContainers.get(1).size() > 0) {
					out.println("    HeavyContainer: " + String.join(" ", shipContainers.get(1)));
				}
				if(shipContainers.get(2).size() > 0) {
					out.println("    RefrigeratedContainer: " + String.join(" ", shipContainers.get(2)));
				}
				if(shipContainers.get(3).size() > 0) {
					out.println("    LiquidContainer: " + String.join(" ", shipContainers.get(3)));
				}
				
			}
		}
		
		
		in.close();
		out.close();
	}
}



//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

