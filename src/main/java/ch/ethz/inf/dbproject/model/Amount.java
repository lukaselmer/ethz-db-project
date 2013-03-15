package ch.ethz.inf.dbproject.model;

/**
 * Object that represents a funding amount.
 */
public class Amount {

	private final double amount;
	private final String reward;
	
	public Amount(final double amount, final String reward) {
		this.amount = amount;
		this.reward = reward;
	}

	public double getAmount() {
		return amount;
	}

	public String getReward() {
		return reward;
	}		
}
