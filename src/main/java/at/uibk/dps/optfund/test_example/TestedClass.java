package at.uibk.dps.optfund.test_example;

/**
 * A class to demonstrate unit tests and coverage measurement.
 * 
 * @author fedor
 *
 */
public class TestedClass {

	protected final int firstSummand;
	protected final int secondSummand;

	/**
	 * Default constructor
	 * 
	 * @param firstSummand  the first summand
	 * @param secondSummand the second summand
	 */
	public TestedClass(final int firstSummand, final int secondSummand) {
		this.firstSummand = firstSummand;
		this.secondSummand = secondSummand;
	}

	/**
	 * Returns the sum of the two summands defined during construction.
	 * 
	 * @return the sum of the two summands defined during construction
	 */
	public int getSum() {
		return firstSummand + secondSummand;
	}
}
