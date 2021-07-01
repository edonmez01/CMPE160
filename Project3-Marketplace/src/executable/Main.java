package executable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import elements.Market;
import elements.Trader;

/**
 * This program is a simple currency marketplace implementation, written as the third project of the CMPE160 course.
 * In this program, there is a single market in which multiple traders place buying and selling orders of a fictional
 * currency named PQoin. The market takes a small fee from each transaction processed. The market also has other
 * abilities such as rewarding all traders with random amounts of PQoin, or keeping track of the number of successful
 * transactions and invalid queries.
 * @author Eren
 *
 */
public class Main {
	public static Random myRandom;
	
	public static void main(String[] args) {
		Scanner in;
		try {
			in = new Scanner(new File(args[0]));
		} catch (FileNotFoundException e) {
			System.out.println("Input file not found.");
			e.printStackTrace();
			return;
		}
		
		PrintStream out;
		try {
			out = new PrintStream(new File(args[1]));
		} catch (FileNotFoundException e) {
			System.out.println("Output file not found");
			e.printStackTrace();
			return;
		}
		
		long seed = Long.parseLong(in.nextLine());
		myRandom = new Random(seed);
		
		String[] tokens = in.nextLine().split(" ");
		int marketFee = Integer.parseInt(tokens[0]);
		int numOfUsers = Integer.parseInt(tokens[1]);
		int numOfQueries = Integer.parseInt(tokens[2]);
		
		Market market = new Market(marketFee);
		
		for(int i = 0; i < numOfUsers; i++) {
			tokens = in.nextLine().split(" ");
			double dollars = Double.parseDouble(tokens[0]);
			double coins = Double.parseDouble(tokens[1]);
			new Trader(dollars, coins);
		}
		
		for(int i = 0; i < numOfQueries; i++) {
			tokens = in.nextLine().split(" ");
			int tokenID = Integer.parseInt(tokens[0]);
			
			if(tokenID == 10) {
				int traderID = Integer.parseInt(tokens[1]);
				double price = Double.parseDouble(tokens[2]);
				double amount = Double.parseDouble(tokens[3]);
				
				if(Market.traders.get(traderID).buy(amount, price, market) == 0) {
					Market.invalidQueries++;
				}
				
			} else if(tokenID == 11) {
				int traderID = Integer.parseInt(tokens[1]);
				double amount = Double.parseDouble(tokens[2]);
				double price = market.getTopSellingPrice();
				if(price < 0) {
					Market.invalidQueries++;
				} else if(Market.traders.get(traderID).buy(amount, price, market) == 0) {
					Market.invalidQueries++;
				}
				
			} else if(tokenID == 20) {
				int traderID = Integer.parseInt(tokens[1]);
				double price = Double.parseDouble(tokens[2]);
				double amount = Double.parseDouble(tokens[3]);
				
				if(Market.traders.get(traderID).sell(amount, price, market) == 0) {
					Market.invalidQueries++;
				}
				
			} else if(tokenID == 21) {
				int traderID = Integer.parseInt(tokens[1]);
				double amount = Double.parseDouble(tokens[2]);
				double price = market.getTopBuyingPrice();
				if(price < 0) {
					Market.invalidQueries++;
				} else if(Market.traders.get(traderID).sell(amount, price, market) == 0) {
					Market.invalidQueries++;
				}
				
			} else if(tokenID == 3) {
				int traderID = Integer.parseInt(tokens[1]);
				double amount = Double.parseDouble(tokens[2]);
				
				Market.traders.get(traderID).deposit(amount);
				
			} else if(tokenID == 4) {
				int traderID = Integer.parseInt(tokens[1]);
				double amount = Double.parseDouble(tokens[2]);
				
				if(!Market.traders.get(traderID).withdraw(amount)) {
					Market.invalidQueries++;
				}
				
			} else if(tokenID == 5) {
				int traderID = Integer.parseInt(tokens[1]);
				double totalDollars = Market.traders.get(traderID).getTotalDollars();
				double totalCoins = Market.traders.get(traderID).getTotalCoins();
				
				out.printf("Trader %d: %.5f$ %.5fPQ\n", traderID, totalDollars, totalCoins);
				
			} else if(tokenID == 777) {
				for(Trader trader : Market.traders) {
					trader.addCoins(myRandom.nextDouble() * 10);
				}
				
			} else if(tokenID == 666) {
				double price = Double.parseDouble(tokens[1]);
				
				market.makeOpenMarketOperation(price);
				
			} else if(tokenID == 500) {
				double dollarSize = market.totalDollarInBuying();
				double PQSize = market.totalPQInSelling();
				
				out.printf("Current market size: %.5f %.5f\n", dollarSize, PQSize);
				
			} else if(tokenID == 501) {
				out.printf("Number of successful transactions: %d\n", Market.successfulTransactions);
				
			} else if(tokenID == 502) {
				out.printf("Number of invalid queries: %d\n", Market.invalidQueries);
				
			} else if(tokenID == 505) {
				double smallPrice = market.getTopBuyingPrice();
				double bigPrice = market.getTopSellingPrice();
				double avg;
				
				if(smallPrice < 0 && bigPrice < 0) {
					smallPrice = .0;
					bigPrice = .0;
					avg = .0;
				} else if(bigPrice < 0) {
					bigPrice = .0;
					avg = smallPrice;
				} else if(smallPrice < 0) {
					smallPrice = 0;
					avg = bigPrice;
				} else {
					avg = (smallPrice + bigPrice) / 2;
				}
				
				out.printf("Current prices: %.5f %.5f %.5f\n", smallPrice, bigPrice, avg);
				
			} else if(tokenID == 555) {
				for(int j = 0; j < Market.traders.size(); j++) {
					Trader trader = Market.traders.get(j);
					double totalDollars = trader.getTotalDollars();
					double totalCoins = trader.getTotalCoins();
					out.printf("Trader %d: %.5f$ %.5fPQ\n", j, totalDollars, totalCoins);
				}
			}
		}
		
		// Resetting static fields for easy testing
		Market.traders = new ArrayList<Trader>();
		Market.successfulTransactions = 0;
		Market.invalidQueries = 0;
		Trader.numberOfUsers = 0;
	}
}

