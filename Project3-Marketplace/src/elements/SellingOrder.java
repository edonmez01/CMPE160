package elements;

/**
 * This class represents selling orders in the market.
 * @author Eren
 *
 */
public class SellingOrder extends Order implements Comparable<SellingOrder>{

	/**
	 * Constructor for the SellingOrder class. It calls the constructor of the Order class directly.
	 * @param traderID ID of the trader that has placed the order.
	 * @param amount Amount of PQoins in the order.
	 * @param price The price of each PQoin in the order.
	 */
	public SellingOrder(int traderID, double amount, double price) {
		super(traderID, amount, price);
	}

	/**
	 * This method is overridden as part of the Comparable interface, in order to sort the selling orders appropriately
	 * in a priority queue.
	 */
	@Override
	public int compareTo(SellingOrder o) {
		if(this.price < o.price) {
			return -1;
		} else if(this.price > o.price) {
			return 1;
		} else {
			if(this.amount > o.amount) {
				return -1;
			} else if(this.amount < o.amount) {
				return 1;
			} else {
				return this.traderID - o.traderID;
			}
		}
	}

}
