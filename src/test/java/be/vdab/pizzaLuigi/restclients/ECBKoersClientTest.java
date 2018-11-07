package be.vdab.pizzaLuigi.restclients;

import static org.junit.Assert.assertTrue;
import java.math.BigDecimal;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class) 
@Import(ECBKoersClient.class)
@PropertySource("application.properties")
public class ECBKoersClientTest {
	
	@Autowired
	private ECBKoersClient client;
	
	@Before
	public void before() {
		
	}
	
	@Test
	public void test() {
		BigDecimal dollarKoers=client.getDollarKoers();
		int verg=dollarKoers.compareTo(BigDecimal.ZERO);
		//dollarkoers moet positief bedrag zijn
		assertTrue(verg>0);			
	}
}
