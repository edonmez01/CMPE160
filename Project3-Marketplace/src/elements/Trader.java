package elements;

/**
 * This class represents traders, who have the ability to place buying and selling orders in the market. Every trader
 * has a wallet which contains dollars and PQoins.
 * @author Eren
 *
 */
public class Trader {
	
	/**
	 * ID of the trader. IDs of traders start from 0 and are incremented by 1 with each new trader. The trader with ID
	 * 0 is the market itself, which is utilized during order #666.
	 */
	private int id;

	/**
	 * Wallet of the trader that keeps track of the amount of dollars and PQoins the trader has.
	 */
	private Wallet wallet;
	
	/**
	 * The current number of traders in the market. This variable is used in order to determine the ID of the next
	 * trader.
	 */
	public static int numberOfUsers = 0;
	
	/**
	 * Constructor for the Trader class.
	 * @param dollars The initial amount of dollars the trader has.
	 * @param coins The initial amount of PQoins the trader has.
	 */
	public Trader(double dollars, double coins) {
		this.id = Trader.numberOfUsers;
		Trader.numberOfUsers++;		
		this.wallet = new Wallet(dollars, coins);
		Market.traders.add(this);
	}
	
	/**
	 * This method is called when the trader is attempting to place a selling order in the market.
	 * @param amount The amount of PQoins the trader wants to sell.
	 * @param price The price the trader wants to sell their PQoins at.
	 * @param market The market.
	 * @return 1 if the selling order is placed successfully, 0 otherwise.
	 */
	public int sell(double amount, double price, Market market) {
		if(price >= 0 && this.wallet.blockCoins(amount)) {
			market.giveSellOrder(new SellingOrder(this.id, amount, price));
			return 1;
		} else {
			return 0;
		}
	}
	
	/**
	 * This method is called when the trader is attempting to place a buying order in the market.
	 * @param amount The amount of PQoins the trader wants to buy.
	 * @param price The price the trader wants to buy PQoins at.
	 * @param market The market.
	 * @return 1 if the buying order is placed successfully, 0 otherwise.
	 */
	public int buy(double amount, double price, Market market) {
		if(price >= 0 && this.wallet.blockDollars(amount * price)) {
			market.giveBuyOrder(new BuyingOrder(this.id, amount, price));
			return 1;
		} else {
			return 0;
		}
	}
	
	/**
	 * This method is called after the trader has successfully sold a given amount of PQoins at a given price. The
	 * purpose of this method is to make the appropriate modifications in the wallet of the trader.
	 * @param amount The amount of PQoins the trader has sold.
	 * @param price The price the trader sold their PQoins at.
	 * @param marketFee The market fee per 1000 dollars.
	 */
	void finalizeSelling(double amount, double price, int marketFee) {
		this.wallet.addDollars(amount * price * (1 - (double) marketFee / 1000));
		this.wallet.removeCoins(amount);
	}
	
	/**
	 * This method is called after the trader has successfully bought a given amount of PQoins at a given price. The
	 * purpose of this method is to make the appropriate modifications in the wallet of the trader.
	 * @param amount The amount of PQoins the trader has bought.
	 * @param price The price the trader bought the PQoins at.
	 * @param marketFee The market fee per 1000 dollars.
	 */
	void finalizeBuying(double amount, double intendedPrice, double actualPrice) {
		this.wallet.addCoins(amount);
		this.wallet.removeDollars(amount * actualPrice);
		this.wallet.unblockDollars(amount * (intendedPrice - actualPrice));
	}
	
	/**
	 * This method is for depositing dollars in the trader's wallet.
	 * @param amount The amount of dollars to be deposited.
	 */
	public void deposit(double amount) {
		this.wallet.addDollars(amount);
	}
	
	/**
	 * This method is for withdrawing dollars from the trader's wallet.
	 * @param amount The amount of dollars to be withdrawn.
	 * @return true if the given amount of dollars can be withdrawn, false otherwise.
	 */
	public boolean withdraw(double amount) {
		return this.wallet.withdrawDollars(amount);
	}
	/**
	 * This method returns the total number of dollars in the trader's wallet, including blocked dollars.
	 * @return Total number of dollars in the trader's wallet.
	 */
	public double getTotalDollars() {
		return this.wallet.getTotalDollars();
	}
	
	/**
	 * This method returns the total number of PQoins in the trader's wallet, including blocked PQoins.
	 * @return Total number of PQoins in the trader's wallet.
	 */
	public double getTotalCoins() {
		return this.wallet.getTotalCoins();
	}
	
	/**
	 * This method is for adding a given number of PQoins to the trader's wallet.
	 * @param amount The number of PQoins to be added to the trader's wallet.
	 */
	public void addCoins(double amount) {
		this.wallet.addCoins(amount);
	}
	
}
