package be.vdab.pizzaLuigi.entities;

import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.SafeHtml;

public class Pizza {
	private int id;	
	@NotBlank
	@SafeHtml
	private String naam;
	@NotNull 
	@PositiveOrZero
	private BigDecimal prijs;	
	private boolean pikant;
	
	public Pizza() {
		// TODO Auto-generated constructor stub
	}

	public Pizza(String naam, BigDecimal prijs, boolean pikant) {
		this.naam = naam;
		this.prijs = prijs;
		this.pikant = pikant;
	}
	
	public Pizza(int id, String naam, BigDecimal prijs, boolean pikant) {
		this.id = id;
		this.naam = naam;
		this.prijs = prijs;
		this.pikant = pikant;
	}

	public int getId() {
		return id;
	}

	public String getNaam() {
		return naam;
	}

	public boolean isPikant() {
		return pikant;
	}
	
	public BigDecimal getPrijs() {
		return prijs;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public void setPrijs(BigDecimal prijs) {
		this.prijs = prijs;
	}

	public void setPikant(boolean pikant) {
		this.pikant = pikant;
	}
}
