package at.uibk.dps.optfund.test_example;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestedClassTest {

	@Test
	public void test() {
		TestedClass tested = new TestedClass(40, 2);
		assertEquals(42, tested.getSum());
	}
}
