import org.junit.Test;
import org.junit.Assert;

public class SeleccionadorGoldbergTest {

	@Test 
	public void domina() {
		Double[] elem1 = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0};
		Double[] elem2 = {2.0, 3.0, 4.0, 5.0, 6.0, 7.0};
		Double[] elem3 = {0.0, 3.0, 4.0, 5.0, 6.0, 7.0};
		SeleccionadorGoldberg selec = new SeleccionadorGoldberg();
		Assert.assertTrue(selec.domina(elem1, elem2));
		Assert.assertFalse(selec.domina(elem2, elem1));
		Assert.assertFalse(selec.domina(elem1, elem3));
		Assert.assertFalse(selec.domina(elem3, elem1));
	}

}
