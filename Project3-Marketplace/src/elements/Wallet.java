package elements;

/**
 * This class represents wallets that have the ability to contain both dollars and PQoins. Each trader has exactly
 * one wallet.
 * @author Eren
 *
 */
public class Wallet {
	
	/**
	 * The amount of unblocked dollars in the wallet.
	 */
	private double dollars;
	
	/**
	 * The amount of unblocked PQoins in the wallet.
	 */
	private double coins;
	
	/**
	 * The amount of blocked dollars in the wallet.
	 */
	private double blockedDollars;
	
	/**
	 * The amount of blocked PQoins in the wallet.
	 */
	private double blockedCoins;

	/**
	 * Constructor for the wallet class.
	 * @param dollars The initial amount of dollars in the wallet.
	 * @param coins The initial amount of PQoins in the wallet.
	 */
	public Wallet(double dollars, double coins) {
		this.dollars = dollars;
		this.coins = coins;
		this.blockedDollars = .0;
		this.blockedCoins = .0;
	}
	
	/**
	 * This method is for blocking a given amount of dollars in the wallet.
	 * @param amount The amount of dollars to be blocked.
	 * @return true if the operation has been carried out successfully, false otherwise.
	 */
	boolean blockDollars(double amount) {
		if(this.dollars >= amount - 1e-6) {
			this.dollars -= amount;
			this.blockedDollars += amount;
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * This method is for blocking a given amount of PQoins in the wallet.
	 * @param amount The amount of PQoins to be blocked.
	 * @return true if the operation has been carried out successfully, false otherwise.
	 */
	boolean blockCoins(double amount) {
		if(this.coins >= amount - 1e-6) {
			this.coins -= amount;
			this.blockedCoins += amount;
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * This method is for unblocking a given amount of dollars in the wallet.
	 * @param amount The amount of dollars to be unblocked.
	 */
	void unblockDollars(double amount) {
		this.blockedDollars -= amount;
		this.dollars += amount;
	}
	
	/**
	 * This method is for permanently removing a given amount of unblocked dollars from the wallet.
	 * @param amount The amount of dollars to be removed.
	 * @return true if the operation has been carried out successfully, false otherwise.
	 */
	boolean withdrawDollars(double amount) {
		if(this.dollars >= amount - 1e-6) {
			this.dollars -= amount;
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * This method is for permanently removing a given amount of blocked dollars from the wallet.
	 * @param amount The amount of dollars to be removed.
	 * @return true if the operation has been carried out successfully, false otherwise.
	 */
	boolean removeDollars(double amount) {
		if(this.blockedDollars >= amount - 1e-6) {
			this.blockedDollars -= amount;
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * This method is for permanently removing a given amount of blocked PQoins from the wallet.
	 * @param amount The amount of PQoins to be removed.
	 * @return true if the operation has been carried out successfully, false otherwise.
	 */
	boolean removeCoins(double amount) {
		if(this.blockedCoins >= amount - 1e-6) {
			this.blockedCoins -= amount;
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * This method is for adding a given amount of dollars to the wallet.
	 * @param amount The amount of dollars to be added.
	 */
	void addDollars(double amount) {
		this.dollars += amount;
	}
	
	/**
	 * This method is for adding a given amount of PQoins to the wallet.
	 * @param amount The amount of PQoins to be added.
	 */
	void addCoins(double amount) {
		this.coins += amount;
	}
	
	/**
	 * This method returns the total number of dollars in the wallet, including blocked dollars.
	 * @return Total number of dollars in the trader's wallet.
	 */
	double getTotalDollars() {
		return this.dollars + this.blockedDollars;
	}
	
	/**
	 * This method returns the total number of PQoins in the wallet, including blocked PQoins.
	 * @return Total number of PQoins in the trader's wallet.
	 */
	double getTotalCoins() {
		return this.coins + this.blockedCoins;
	}
}
