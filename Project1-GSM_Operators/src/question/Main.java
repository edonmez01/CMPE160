
package question;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class Main {


	public static void main(String args[]) {

		Customer[] customers;
		Operator[] operators;

		int C, O, N;

		File inFile = new File(args[0]);  // args[0] is the input file
		File outFile = new File(args[1]);  // args[1] is the output file
//		try {
//			PrintStream outstream = new PrintStream(outFile);
//		} catch (FileNotFoundException e1) {
//			e1.printStackTrace();
//		}
		
		PrintStream outstream1;
		try {
		    outstream1 = new PrintStream(outFile);
		}catch(FileNotFoundException e2) {
		    e2.printStackTrace();
		    return;
		}

		Scanner reader;
		try {
			reader = new Scanner(inFile);
		} catch (FileNotFoundException e) {
			System.out.println("Cannot find input file");
			return;
		}

		C = reader.nextInt();
		O = reader.nextInt();
		N = reader.nextInt();

		customers = new Customer[C];
		operators = new Operator[O];

		//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE
		
		while(reader.hasNextLine()){
			String line = reader.nextLine();
			if(line.isEmpty()) {
				continue;
			}
			
			String[] tokens = line.split(" ");
			String identifier = tokens[0];
			
			if(identifier.equals("1")){  // Create a new customer
				String name = tokens[1];
				int age = Integer.parseInt(tokens[2]);
				int ID = Integer.parseInt(tokens[3]);
				double limitingAmount = Double.parseDouble(tokens[4]);
				int customerID = Customer.nextCustomerID;
				Operator operator = operators[ID];
				Customer customer = new Customer(customerID, name, age, operator, limitingAmount);
				customers[customerID] = customer;
				
			} else if(identifier.equals("2")) {  // Create a new operator
				double talkingCharge = Double.parseDouble(tokens[1]);
				double messageCost = Double.parseDouble(tokens[2]);
				double networkCharge = Double.parseDouble(tokens[3]);
				int discountRate = Integer.parseInt(tokens[4]);
				int ID = Operator.nextOperatorID;
				Operator operator = new Operator(ID, talkingCharge, messageCost, networkCharge, discountRate);
				operators[ID] = operator;
				
			} else if(identifier.equals("3")) {  // Talk to another customer
				int customerID1 = Integer.parseInt(tokens[1]);
				int customerID2 = Integer.parseInt(tokens[2]);
				int time = Integer.parseInt(tokens[3]);
				Customer customer1 = customers[customerID1];
				Customer customer2 = customers[customerID2];
				 
				if(customer1.canTalkTo(customer2, time)) {
					customer1.talk(time, customer2);
				}
	
			} else if(identifier.equals("4")) {  // Message another customer
				int customerID1 = Integer.parseInt(tokens[1]);
				int customerID2 = Integer.parseInt(tokens[2]);
				int quantity = Integer.parseInt(tokens[3]);
				Customer customer1 = customers[customerID1];
				Customer customer2 = customers[customerID2];
				
				if(customer1.canMessage(customer2, quantity)) {
					customer1.message(quantity, customer2);
				}
				
			} else if(identifier.equals("5")) {  // Connect to the Internet
				int customerID = Integer.parseInt(tokens[1]);
				double amount = Double.parseDouble(tokens[2]);
				Customer customer = customers[customerID];
				
				if(customer.canConnect(amount)) {
					customer.connection(amount);
				}
				
			} else if(identifier.equals("6")) {  // Pay bills
				int customerID = Integer.parseInt(tokens[1]);
				double amount = Double.parseDouble(tokens[2]);
				Customer customer = customers[customerID];
				customer.getBill().pay(customer.moneyToPay(amount));
				
			} else if(identifier.equals("7")) {  // Change operator
				int customerID = Integer.parseInt(tokens[1]);
				int operatorID = Integer.parseInt(tokens[2]);
				Customer customer = customers[customerID];
				Operator operator = operators[operatorID];
				customer.setOperator(operator);
				
			} else if(identifier.equals("8")) {  // Change bill limit
				int customerID = Integer.parseInt(tokens[1]);
				double newLimit = Double.parseDouble(tokens[2]);
				Customer customer = customers[customerID];
				customer.getBill().changeTheLimit(newLimit);
				
			}
			
		}
		
		// OUTPUT
		for(Operator operator : operators) {
			int ID = operator.getID();
			int timeServiced = operator.timeServiced;
			int messagesSent = operator.messagesSent;
			double internetUsage = operator.internetUsage;
			outstream1.println(String.format("Operator %d : %d %d %.2f", ID, timeServiced, messagesSent, internetUsage));  // Output 1
		}
		
		int maxTalkedID = 0;
		int maxTalkedTime = customers[0].getTimeTalked();
		
		int maxMessagesID = 0;
		int maxMessagesAmount = customers[0].getMessagesSent();
		
		int maxConnectionID = 0;
		double maxConnectionAmount = customers[0].getInternetUsage();
		
		for (Customer customer : customers) {  
			int ID = customer.getID();
			
			// The following three if clauses are for detecting the customers that talked the most (Output 3), messaged the most (Output 4), and connected the most (Output 5).
			if(customer.getTimeTalked() > maxTalkedTime) {
				maxTalkedTime = customer.getTimeTalked();
				maxTalkedID = ID;
			}
			
			if(customer.getMessagesSent() > maxMessagesAmount) {
				maxMessagesAmount = customer.getMessagesSent();
				maxMessagesID = ID;
			}
			
			if(customer.getInternetUsage() > maxConnectionAmount) {
				maxConnectionAmount = customer.getInternetUsage();
				maxConnectionID = ID;
			}
			
			double totalMoneyPaid = customer.getTotalMoneyPaid();
			double debt = customer.getBill().getCurrentDebt();
			outstream1.println(String.format("Customer %d : %.2f %.2f", ID, totalMoneyPaid, debt));  // Output 2
		}
		
		String maxTalkedName = customers[maxTalkedID].getName();
		String maxMessagesName = customers[maxMessagesID].getName();
		String maxConnectionName = customers[maxConnectionID].getName();
		outstream1.println(maxTalkedName + " : " + maxTalkedTime);  // Output 3
		outstream1.println(maxMessagesName + " : " + maxMessagesAmount);  // Output 4
		outstream1.println(maxConnectionName + String.format(" : %.2f", maxConnectionAmount));  // Output 5
		
		// To prevent resource leaks:
		reader.close();
		outstream1.close();
		
		// Looks like not resetting static fields at the end of the program causes some kind of problem in JUnit tests, so there we go:
		Customer.nextCustomerID = 0;
		Operator.nextOperatorID = 0;
		
		//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE
	} 
}

