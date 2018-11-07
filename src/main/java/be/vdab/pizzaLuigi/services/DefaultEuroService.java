package be.vdab.pizzaLuigi.services;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import be.vdab.pizzaLuigi.restclients.KoersClient;

@Service
class DefaultEuroService implements EuroService {
	
	private KoersClient koersClient;

	public DefaultEuroService(@Qualifier("Fixer") KoersClient koersClient) {
		this.koersClient = koersClient;
	}
	
	@Override
	public BigDecimal naarDollar(BigDecimal euro) {
		BigDecimal dollarKoers=koersClient.getDollarKoers();
		BigDecimal dollar=euro.multiply(dollarKoers);
		
		return dollar.setScale(2, RoundingMode.HALF_UP);
	}
}
