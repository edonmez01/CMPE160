
package question;

public class Operator {
	//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE
	private int ID;
	private double talkingCharge;
	private double messageCost;
	private double networkCharge;
	private int discountRate;
	
	public int timeServiced;
	public int messagesSent;
	public double internetUsage;
	public static int nextOperatorID = 0;
	
	/**
	 * Constructor for the Operator class. {@link #nextOperatorID Operator.nextOperatorID} is incremented by 1 with each new customer.
	 * 
	 * @param ID ID of the operator. Determined by the initialization order of the operator. (First operator created has ID 0, the next operator has 1, and so on.)
	 * @param talkingCharge The amount of money the operator charges for talking per minute.
	 * @param messageCost The amount of money the operator charges per message.
	 * @param networkCharge The amount of money the operator charges per Megabyte of Internet connection.
	 * @param discountRate The rate of discount the operator applies to children and elderly customers while talking; and to customers of the same operator while messaging.
	 */
	public Operator(int ID, double talkingCharge, double messageCost, double networkCharge, int discountRate) {
		this.ID = ID;
		this.talkingCharge = talkingCharge;
		this.messageCost = messageCost;
		this.networkCharge = networkCharge;
		this.discountRate = discountRate;
		this.timeServiced = 0;
		this.messagesSent = 0;
		this.internetUsage = .0;
		Operator.nextOperatorID++;
		
	}
	
	/**
	 * Calculates the amount a customer will pay for talking for a given amount of time. If the customer is under the age of 18 or over the age of 65, a certain discount
	 * specified by {@link #discountRate discountRate} is applied.
	 * 
	 * @param minute The duration of the call.
	 * @param customer The caller.
	 * 
	 * @return The cost of the operation.
	 */
	public double calculateTalkingCost(int minute, Customer customer) {
		if(customer.getAge() < 18 || customer.getAge() > 65) {
			return minute * this.talkingCharge * (double) (100 - this.discountRate) / 100;
		} else {
			return minute * this.talkingCharge;
		}
	}
	
	/**
	 * Calculates the amount a customer will pay for messaging another customer a given number of times. If two customers have the same operator, a certain discount specified
	 * by {@link #discountRate discountRate} is applied.
	 * 
	 * @param quantity The number of messages to be sent.
	 * @param customer The sender.
	 * @param other The receiver.
	 * 
	 * @return The cost of the operation.
	 */
	public double calculateMessageCost(int quantity, Customer customer, Customer other) {
		if(this.getID() == other.getOperator().getID()) {
			return quantity * this.messageCost * (double) (100 - this.discountRate) / 100;
		} else {
			return quantity * this.messageCost;
		}
	}
	
	/**
	 * Calculates the amount a customer will pay for connecting to the Internet using a given amount of bandwidth.
	 * 
	 * @param amount The amount of bandwidth to be used.
	 * 
	 * @return The cost of the operation.
	 */
	public double calculateNetworkCost(double amount) {
		return amount * this.networkCharge;
	}
	
	/**
	 * Getter method for {@link #ID ID}.
	 * 
	 * @return ID of the operator.
	 */
	public int getID() {
		return this.ID;
	}

	/**
	 * Getter method for {@link #talkingCharge talkingCharge}.
	 * 
	 * @return Talking charge per minute of the operator.
	 */
	public double getTalkingCharge() {
		return this.talkingCharge;
	}

	/**
	 * Getter method for {@link #messageCost messageCost}.
	 * 
	 * @return Message cost of the operator.
	 */
	public double getMessageCost() {
		return this.messageCost;
	}

	/**
	 * Getter method for {@link #networkCharge networkCharge}.
	 * 
	 * @return Network charge per Megabyte of the operator.
	 */
	public double getNetworkCharge() {
		return this.networkCharge;
	}

	/**
	 * Getter method for {@link #discountRate discountRate}.
	 * 
	 * @return Discount rate the operator applies to children and elderly customers while talking; and to customers of the same operator while messaging.
	 */
	public int getDiscountRate() {
		return this.discountRate;
	}

	/**
	 * Setter method for {@link #talkingCharge talkingCharge}.
	 * 
	 * @param talkingCharge New talking charge per minute of the operator.
	 */
	public void setTalkingCharge(double talkingCharge) {
		this.talkingCharge = talkingCharge;
	}

	/**
	 * Setter method for {@link #messageCost messageCost}.
	 * 
	 * @param messageCost New message cost of the operator.
	 */
	public void setMessageCost(double messageCost) {
		this.messageCost = messageCost;
	}

	/**
	 * Setter method for {@link #networkCharge networkCharge}.
	 * 
	 * @param networkCharge New network charge per Megabyte of the operator.
	 */
	public void setNetworkCharge(double networkCharge) {
		this.networkCharge = networkCharge;
	}

	/**
	 * Setter method for {@link #discountRate discountRate}.
	 * 
	 * @param discountRate New discount rate the operator applies to children and elderly customers while talking; and to customers of the same operator while messaging.
	 */
	public void setDiscountRate(int discountRate) {
		this.discountRate = discountRate;
	}

	//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE
}

