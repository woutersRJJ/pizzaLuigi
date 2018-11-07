package be.vdab.pizzaLuigi.repositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import be.vdab.pizzaLuigi.entities.Pizza;

interface PizzaRepository {
	
	int findAantalPizzas();
	void create(Pizza pizza);
	void update(Pizza pizza);
	void delete(int id);
	
	Optional<Pizza>read(int id);
	List<Pizza>findAll();
	List<Pizza>findByPrijs(BigDecimal prijs);
	List<Pizza>findByPrijsBetween(BigDecimal van, BigDecimal tot);
	List<BigDecimal>findUniekePrijzen();
}
