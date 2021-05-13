
package question;

public class Customer {
	
	//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE
	private int ID;
	private String name;
	private int age;
	private Operator operator;
	private Bill bill;
	private double totalMoneyPaid;
	private int timeTalked;
	private int messagesSent;
	private double internetUsage;
	
	public static int nextCustomerID = 0;
	
	/**
	 * Constructor for the Customer class. With each instance of Customer, a Bill dedicated to the customer is created, with the specified limiting amount. Also,
	 * {@link #nextCustomerID Customer.nextCustomerID} is incremented by 1 with each new instance of Customer.
	 * 
	 * @param ID ID of the customer. Determined by the initialization order of the customer. (First customer created has ID 0, the next customer has 1, and so on.)
	 * @param name Name of the customer.
	 * @param age Age of the customer. Operators offer a discount to children and elderly customers. Therefore, this field is required to determine if the customer receives a discount.
	 * @param operator Operator of the customer.
	 * @param limitingAmount Limiting amount of the customer.
	 */
	public Customer(int ID, String name, int age, Operator operator, double limitingAmount) {
		this.ID = ID;
		this.name = name;
		this.age = age;
		this.operator = operator;
		this.bill = new Bill(limitingAmount);
		this.totalMoneyPaid = 0;
		this.timeTalked = 0;
		this.messagesSent = 0;
		this.internetUsage = .0;
		Customer.nextCustomerID++;
	}
	
	/**
	 * Simulates a customer talking to another customer. This method should be called only if the customer can afford the cost of talking.
	 * Only the caller is charged for this operation, the other customer doesn't pay any fees.
	 * The price the caller pays is calculated by calling the {@link Operator#calculateTalkingCost(int, Customer) Operator.calculateTalkingCost} method.
	 * 
	 * @param minute Duration of the call.
	 * @param other The customer answering the call. This should be different than the customer calling the method.
	 */
	public void talk(int minute, Customer other) {
		double price = this.operator.calculateTalkingCost(minute, this);
		this.bill.add(price);
		this.operator.timeServiced += minute;
		other.operator.timeServiced += minute;
		this.timeTalked += minute;
		other.timeTalked += minute;
	}
	
	/**
	 * Simulates a customer messaging another customer. This method should only be called if the customer can afford the cost of messaging.
	 * Only the sender is charged for this operation, the other customer doesn't pay any fees.
	 * The price the sender pays is calculated by calling the {@link Operator#calculateMessageCost(int, Customer, Customer) Operator.calculateMessageCost} method.
	 * 
	 * @param quantity Quantity of messages to be sent.
	 * @param other The customer receiving the message(s). This should be different than the customer calling the method.
	 */
	public void message(int quantity, Customer other) {
		double price = this.operator.calculateMessageCost(quantity, this, other);
		this.bill.add(price);
		this.operator.messagesSent += quantity;
		this.messagesSent += quantity;
	}
	
	/**
	 * Simulates a customer connecting to the Internet. This method should only be called if the customer can afford the cost of connecting to the Internet.
	 * The price the customer pays is calculated by the {@link Operator#calculateNetworkCost(double) Operator.calculateNetworkCost} method.
	 * 
	 * @param amount Amount of bandwidth the customer uses (in Megabytes).
	 */
	public void connection(double amount) {
		double price = this.operator.calculateNetworkCost(amount);
		this.bill.add(price);
		this.operator.internetUsage += amount;
		this.internetUsage += amount;

	}
	
	/**
	 * This method is called before the {@link Bill#pay(double) Bill.pay} method to calculate how much the customer will pay.
	 * It also tracks the total money paid by the customer.
	 * 
	 * @param amount The amount of money the customer wants to pay. If this is higher than the customer's debt, the customer only pays the amount of debt they have.
	 * 
	 * @return The amount of money the customer pays.
	 */
	public double moneyToPay(double amount) {
		if(amount > this.bill.getCurrentDebt()) {
			this.totalMoneyPaid += this.bill.getCurrentDebt();
			return this.bill.getCurrentDebt();
		} else {
			this.totalMoneyPaid += amount;
			return amount;
		}
	}
	
	/**
	 * Checks if the customer will be able to talk to a given customer for a given amount of time. The operation can't take place if the two customers are the same or if the caller
	 * cannot afford this operation.
	 * 
	 * @param otherCustomer Receiver of the call.
	 * @param duration Duration of the call.
	 * 
	 * @return True if the call can take place, false otherwise.
	 */
	public boolean canTalkTo(Customer otherCustomer, int duration) {
		return this.getBill().check(this.getOperator().calculateTalkingCost(duration, this)) && (this.getID() != otherCustomer.getID());
	}
	
	/**
	 * Checks if the customer will be able to message a given customer a given amount of times. The operation can't take place if the two customers are the same or if the sender
	 * cannot afford this operation.
	 * 
	 * @param otherCustomer Receiver of the message(s).
	 * @param quantity Number of messages.
	 * 
	 * @return True if the message(s) can be sent, false otherwise.
	 */
	public boolean canMessage(Customer otherCustomer, int quantity) {
		return this.getBill().check(this.getOperator().calculateMessageCost(quantity, this, otherCustomer)) && (this.getID() != otherCustomer.getID());
	}
	
	/**
	 * Checks if the customer will be able to connect to the Internet using a given amount of bandwidth. The operation can't take place if the customer
	 * cannot afford this operation.
	 * 
	 * @param amount Amount of bandwidth to be used (in Megabytes).
	 * 
	 * @return True if the connection can take place, false otherwise.
	 */
	public boolean canConnect(double amount) {
		return this.getBill().check(this.getOperator().calculateNetworkCost(amount));
	}
	
	/**
	 * Getter method for {@link #age age}.
	 * 
	 * @return Age of the customer.
	 */
	public int getAge() {
		return this.age;
	}
	
	/**
	 * Getter method for {@link #operator operator}.
	 * 
	 * @return Operator of the customer.
	 */
	public Operator getOperator() {
		return this.operator;
	}
	
	/**
	 * Getter method for {@link #bill bill}.
	 * 
	 * @return Bill of the customer.
	 */
	public Bill getBill() {
		return this.bill;
	}
	
	/**
	 * Getter method for {@link #ID ID}.
	 * 
	 * @return ID of the customer.
	 */
	public int getID() {
		return this.ID;
	}
	
	/**
	 * Getter method for {@link #totalMoneyPaid totalMoneyPaid}.
	 * 
	 * @return Total money the customer has paid since the beginning of the simulation.
	 */
	public double getTotalMoneyPaid() {
		return this.totalMoneyPaid;
	}
	
	/**
	 * Getter method for {@link #timeTalked timeTalked}.
	 * 
	 * @return Total amount of time the customer has talked since the beginning of the simulation.
	 */
	public int getTimeTalked() {
		return this.timeTalked;
	}
	
	/**
	 * Getter method for {@link #messagesSent messagesSent}.
	 * 
	 * @return Total number of messages the customer has sent since the beginning of the simulation.
	 */
	public int getMessagesSent() {
		return this.messagesSent;
	}
	
	/**
	 * Getter method for {@link #internetUsage internetUsage}.
	 * 
	 * @return Total amount of Internet used by the customer since the beginning of the simulation.
	 */
	public double getInternetUsage() {
		return this.internetUsage;
	}
	
	/**
	 * Getter method for {@link #name name}.
	 * 
	 * @return Name of the customer.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Setter method for {@link #age age}.
	 * 
	 * @param age New age of the customer.
	 */
	public void setAge(int age) {
		this.age = age;
	}
	
	/**
	 * Setter method for {@link #operator operator}.
	 * 
	 * @param operator New operator of the customer.
	 */
	public void setOperator(Operator operator) {
		this.operator = operator;
	}
	
	/**
	 * Setter method for {@link #bill bill}.
	 * 
	 * @param bill New bill of the customer.
	 */
	public void setBill(Bill bill) {
		this.bill = bill;
	}
	
	//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE
}

