
package question;

public class Bill {

	//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE
	private double limitingAmount;
	private double currentDebt;

	/**
	 * Constructor for the Bill class. The debt of a customer is always initialized as 0 and the limiting amount
	 * is specified by the customer.
	 * 
	 * @param limitingAmount Limiting amount on the bill, which the customer cannot exceed. This amount can later be
	 * changed by the {@link #changeTheLimit(double) changeTheLimit} method.
	 */
	public Bill(double limitingAmount) {
		this.limitingAmount = limitingAmount;
		this.currentDebt = .0;
	}
	
	/**
	 * Checks if the customer can afford an operation with a given cost.
	 * 
	 * @param amount Cost of the operation that the customer wants.
	 * 
	 * @return True if the customer can afford the operation, false otherwise.
	 */
	public boolean check(double amount) {
		return this.currentDebt + amount <= this.limitingAmount;
	}
	
	/**
	 * Adds a given amount to the bill.
	 * 
	 * @param amount Amount to be added to the bill.
	 */
	public void add(double amount) {
		this.currentDebt += amount;
	}
	
	/**
	 * Decreases a given amount from the bill. The amount must be lower than the customer's current debt.
	 * 
	 * @param amount Amount to be decreased from the bill.
	 * 
	 * @throws IllegalArgumentException if {@code amount} is higher than the customer's current debt.
	 */
	public void pay(double amount) {
		if(amount > this.currentDebt) {
			throw new IllegalArgumentException();
		}
		
		this.currentDebt -= amount;
	}
	
	/**
	 * Changes the limiting amount on the bill to a given amount. If the given amount is lower than the customer's current debt,
	 * no actions are taken and the limiting amount remains unchanged.
	 * 
	 * @param amount New limiting amount.
	 */
	public void changeTheLimit(double amount) {
		if(amount >= this.currentDebt) {
			this.limitingAmount = amount;
		}
	}
	
	/**
	 * Getter method for {@link #limitingAmount limitingAmount}.
	 * 
	 * @return Limiting amount of the customer.
	 */
	public double getLimitingAmount() {
		return this.limitingAmount;
	}
	
	/**
	 * Getter method for {@link #currentDebt currentDebt}.
	 * 
	 * @return Current debt of the customer.
	 */
	public double getCurrentDebt() {
		return this.currentDebt;
	}
	

	//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE
}

