package pt.iscte.es2.covid_graph_spread;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class CovidGraphSpreadApplicationTest {

	@Test
	void test() {
		CovidGraphSpreadApplication covidGraphSpreadApplication = new CovidGraphSpreadApplication();
		String html = covidGraphSpreadApplication.getCovidGraphSpreadTable();
		
		Assert.assertTrue(html != null);
		Assert.assertNotEquals("Git is null...", html);
	}

}
