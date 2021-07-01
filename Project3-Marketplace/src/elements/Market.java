package elements;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * This class represents the market. Only 1 instance of this is created throughout the whole program, which acts as
 * the sole market.
 * @author Eren
 *
 */
public class Market {
	
	/**
	 * The amount of dollars received by the market for every 1000 dollars processed.
	 */
	private final int marketFee;
	
	/**
	 * The priority queue of all active selling orders. The orders are sorted first by increasing price, then by
	 * decreasing amount, and finally by increasing trader ID.
	 */
	private PriorityQueue<SellingOrder> sellingOrders;
	
	/**
	 * The priority queue of all active buying orders. The orders are sorted first by decreasing price, then by
	 * decreasing amount, and finally by increasing trader ID.
	 */
	private PriorityQueue<BuyingOrder> buyingOrders;
	
	/**
	 * List of all processed transactions. This list has no purpose other than storage.
	 */
	private ArrayList<Transaction> transactions;
	
	/**
	 * List of all traders.
	 */
	public static ArrayList<Trader> traders = new ArrayList<Trader>();
	
	/**
	 * Counter of successful transactions performed.
	 */
	public static int successfulTransactions = 0;
	
	/**
	 * Counter of invalid queries. A query is invalid if a trader is trying to make an operation they can't afford,
	 * or if there are no buying / selling orders when the operation requires at least one.
	 */
	public static int invalidQueries = 0;

	/**
	 * Constructor for the Market class.
	 * @param fee The amount of dollars received by the market for every 1000 dollars processed.
	 */
	public Market(int fee) {
		this.marketFee = fee;
		this.sellingOrders = new PriorityQueue<SellingOrder>();
		this.buyingOrders = new PriorityQueue<BuyingOrder>();
		this.transactions = new ArrayList<Transaction>();
	}
	
	/**
	 * This method adds the given selling order to the priority queue of all selling orders, then checks if any
	 * transactions can be made between traders.
	 * @param order The selling order to be added to the priority queue of all selling orders.
	 */
	public void giveSellOrder(SellingOrder order) {
		sellingOrders.add(order);
		this.checkTransactions(Market.traders);
	}
	
	/**
	 * This method adds the given buying order to the priority queue of all buying orders, then checks if any
	 * transactions can be made between traders.
	 * @param order The buying order to be added to the priority queue of all buying orders.
	 */
	public void giveBuyOrder(BuyingOrder order) {
		buyingOrders.add(order);
		this.checkTransactions(Market.traders);
	}
	
	/**
	 * This is the method for executing order #666. The aim of this method is to try to fix the value of 1 PQoin to a
	 * given price. To accomplish this, the market itself makes appropriate transactions with traders.
	 * @param price The price the market is trying to converge to.
	 */
	public void makeOpenMarketOperation(double price) {
		while(!this.buyingOrders.isEmpty() && this.buyingOrders.peek().price >= price - 1e-6) {
			BuyingOrder buyingOrder = this.buyingOrders.poll();
			int buyerID = buyingOrder.traderID;
			double amount = buyingOrder.amount;
			double orderPrice = buyingOrder.price;
			Transaction transaction = new Transaction(0, buyerID, amount, orderPrice);
			this.transactions.add(transaction);
			
			Trader buyer = Market.traders.get(buyerID);
			buyer.finalizeBuying(amount, orderPrice, orderPrice);
			
			Market.successfulTransactions++;
		}
		while(!this.sellingOrders.isEmpty() && this.sellingOrders.peek().price <= price + 1e-6) {
			SellingOrder sellingOrder = this.sellingOrders.poll();
			int sellerID = sellingOrder.traderID;
			double amount = sellingOrder.amount;
			double orderPrice = sellingOrder.price;
			Transaction transaction = new Transaction(sellerID, 0, amount, orderPrice);
			this.transactions.add(transaction);
			
			Trader seller = Market.traders.get(sellerID);
			seller.finalizeSelling(amount, orderPrice, this.marketFee);
			
			Market.successfulTransactions++;
		}
	}

	/**
	 * This method simulates the market. In other words, it makes transactions between the top buying and selling
	 * orders until no transaction is possible.
	 * @param traders The list of all traders in the market.
	 */
	public void checkTransactions(ArrayList<Trader> traders) {
		while(!(this.sellingOrders.isEmpty() || this.buyingOrders.isEmpty()) && 
				(this.sellingOrders.peek().price <= this.buyingOrders.peek().price + 1e-6)) {
			SellingOrder sellingOrder = this.sellingOrders.poll();
			BuyingOrder buyingOrder = this.buyingOrders.poll();
			double amount = Math.min(sellingOrder.amount, buyingOrder.amount);
			double price = sellingOrder.price;
			double buyersPrice = buyingOrder.price;
			int buyerID = buyingOrder.traderID;
			int sellerID = sellingOrder.traderID;
			Transaction transaction = new Transaction(sellerID, buyerID, amount, price);
			this.transactions.add(transaction);
			
			if(buyingOrder.amount > amount) {
				this.buyingOrders.add(new BuyingOrder(buyerID, buyingOrder.amount - amount, buyersPrice));
			}
			if(sellingOrder.amount > amount) {
				this.sellingOrders.add(new SellingOrder(sellerID, sellingOrder.amount - amount, price));
			}
			
			Trader seller = traders.get(sellerID);
			Trader buyer = traders.get(buyerID);
			
			seller.finalizeSelling(amount, price, this.marketFee);
			buyer.finalizeBuying(amount, buyingOrder.price, price);
			
			Market.successfulTransactions++;
		}
	}
	
	/**
	 * Getter method for the price of the cheapest selling order.
	 * @return -1. if there are no active selling orders, price of the cheapest selling order otherwise.
	 */
	public double getTopSellingPrice() {
		if(this.sellingOrders.isEmpty()) {
			return -1.;
		}
		return this.sellingOrders.peek().price;
	}
	
	/**
	 * Getter method for the price of the most expensive buying order.
	 * @return -1. if there are no active buying orders, price of the most expensive buying order otherwise.
	 */
	public double getTopBuyingPrice() {
		if(this.buyingOrders.isEmpty()) {
			return -1.;
		}
		return this.buyingOrders.peek().price;
	}
	
	/**
	 * This method calculates the total amount of dollars in the market.
	 * @return The total amount of dollars in the market.
	 */
	public double totalDollarInBuying() {
		double out = 0;
		for(BuyingOrder order : this.buyingOrders) {
			out += order.price * order.amount;
		}
		return out;
	}
	
	/**
	 * This method calculates the total amount of PQoins in the market.
	 * @return The total amount of PQoins in the market.
	 */
	public double totalPQInSelling() {
		double out = 0;
		for(SellingOrder order : this.sellingOrders) {
			out += order.amount;
		}
		return out;
	}
}
