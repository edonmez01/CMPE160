package elements;

/**
 * This class represents orders in the market. It is the parent class of the SellingOrder and
 * BuyingOrder classes.
 * @author Eren
 *
 */
public abstract class Order{
	
	/**
	 * Amount of PQoins in the order.
	 */
	double amount;
	
	/**
	 * The price of each PQoin in the order.
	 */
	double price;
	
	/**
	 * ID of the trader that has placed the order.
	 */
	int traderID;
	
	/**
	 * Constructor for the Order class.
	 * @param traderID ID of the trader that has placed the order.
	 * @param amount Amount of PQoins in the order.
	 * @param price The price of each PQoin in the order.
	 */
	public Order(int traderID, double amount, double price) {
		this.amount = amount;
		this.price = price;
		this.traderID = traderID;
	}
}

