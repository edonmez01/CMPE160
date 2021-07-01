package elements;

/**
 * This class represents transactions between two traders. Each transaction consists of 1 buying order and 1 selling
 * order.
 * @author Eren
 *
 */
public class Transaction {
	
	/**
	 * The selling order corresponding to the transaction.
	 */
	private SellingOrder sellingOrder;
	
	/**
	 * The buying order corresponding to the transaction.
	 */
	private BuyingOrder buyingOrder;
	
	/**
	 * Constructor for the Transaction class.
	 * @param sellerID ID of the seller.
	 * @param buyerID ID of the buyer.
	 * @param amount Amount of PQoins to be traded.
	 * @param price The price of each PQoin in the transaction.
	 */
	public Transaction(int sellerID, int buyerID, double amount, double price) {
		this.sellingOrder = new SellingOrder(sellerID, amount, price);
		this.buyingOrder = new BuyingOrder(buyerID, amount, price);
	}
}
