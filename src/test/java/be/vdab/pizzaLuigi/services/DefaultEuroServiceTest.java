package be.vdab.pizzaLuigi.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import be.vdab.pizzaLuigi.restclients.KoersClient;
import be.vdab.pizzaLuigi.services.DefaultEuroService;

@RunWith(MockitoJUnitRunner.class)
public class DefaultEuroServiceTest {
	private EuroService euroService;
	@Mock private KoersClient dummyKoersClient;
	
	@Before
	public void before() {
		//overbodig?
		//dummyKoersClient=mock(KoersClient.class);
		//mock trainen
		euroService = new DefaultEuroService(dummyKoersClient);
		when(dummyKoersClient.getDollarKoers()).thenReturn(BigDecimal.valueOf(1.5)); 	
	}
	
	@Test
	public void naarDollar() {
		BigDecimal euro=BigDecimal.valueOf(2);
		BigDecimal dollar=euroService.naarDollar(euro);
		int verg=dollar.compareTo(BigDecimal.valueOf(3));
		assertEquals(verg,0);
		verify(dummyKoersClient).getDollarKoers();
	}
}
