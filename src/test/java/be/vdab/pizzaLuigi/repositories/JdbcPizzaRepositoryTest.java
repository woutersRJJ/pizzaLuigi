package be.vdab.pizzaLuigi.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

import be.vdab.pizzaLuigi.entities.Pizza;
import be.vdab.pizzaLuigi.exceptions.PizzaNietGevondenException;

@RunWith(SpringRunner.class)
@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(JdbcPizzaRepository.class)
@Sql("/insertPizza.sql")
public class JdbcPizzaRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	JdbcPizzaRepository repository;

	@Test
	public void findAantal() {
		int aant = repository.findAantalPizzas();
		assertEquals(aant, super.countRowsInTable("pizzas"));
	}

	@Test
	public void create() {
		int aantalPizzas = super.countRowsInTable("pizzas");
		Pizza pizza = new Pizza("test2", BigDecimal.TEN, false);
		repository.create(pizza);
		// autonumber ok
		assertNotEquals(0, pizza.getId());
		// 1 pizza meer in tabel
		assertEquals(aantalPizzas + 1, this.countRowsInTable("pizzas"));
		// precies 1 record met deze id
		int aant=super.countRowsInTableWhere("pizzas", "id=" + pizza.getId());
		assertEquals(1, aant);
	}

	@Test
	public void update() {
		int id = idVanTestPizza();
		BigDecimal nieuwePrijs = BigDecimal.valueOf(15);
		Pizza pizza = new Pizza(id, "test", nieuwePrijs, false);
		repository.update(pizza);
		BigDecimal prijsDatabase = super.jdbcTemplate.queryForObject("select prijs from pizzas where id=?",
				BigDecimal.class, id);
		int verg = prijsDatabase.compareTo(nieuwePrijs);
		assertEquals(verg, 0);
	}

	@Test(expected = PizzaNietGevondenException.class)
	public void updateOnbestaandePizza() {
		repository.update(new Pizza(-1, "test", BigDecimal.ONE, false));
	}

	// met SQL opdracht in bestand
	@Test
	public void delete() {
		int id = idVanTestPizza();
		int aantalPizzas = super.countRowsInTable("pizzas");
		repository.delete(id);
		assertEquals(aantalPizzas - 1, super.countRowsInTable("pizzas"));
		assertEquals(0, super.countRowsInTableWhere("pizzas", "id=" + id));
	}

	@Test
	public void read() {
		assertEquals("test", repository.read(idVanTestPizza()).get().getNaam());
	}

	@Test
	public void readOnbestaandePizza() {
		// geen record met id -1
		Optional<Pizza> opt = repository.read(-1);
		assertFalse(opt.isPresent());
	}

	@Test
	public void findAll() {
		List<Pizza> pizzas = repository.findAll();
		assertEquals(super.countRowsInTable("pizzas"), pizzas.size());
		// zijn ze wel oplopend gesorteerd
		int vorigeId = 0;
		for (Pizza pizza : pizzas) {
			assertTrue(pizza.getId() > vorigeId);
			vorigeId = pizza.getId();
		}
	}

	@Test
	public void findByPrijs() {
		List<Pizza> pizzas = repository.findByPrijs(BigDecimal.TEN);
		String vorigeNaam = "";
		for (Pizza pizza : pizzas) {
			assertEquals(0, BigDecimal.TEN.compareTo(pizza.getPrijs()));
			assertTrue(vorigeNaam.compareTo(pizza.getNaam()) <= 0);
			vorigeNaam = pizza.getNaam();
		}
		assertEquals(super.countRowsInTableWhere("pizzas", "prijs=10"), 
				     pizzas.size());
	}

	@Test
	public void findByPrijsBetween() {
		List<Pizza> pizzas = repository.findByPrijsBetween(BigDecimal.ONE, BigDecimal.TEN);
		BigDecimal vorigePrijs = BigDecimal.ZERO;

		for (Pizza pizza : pizzas) {
			assertTrue(pizza.getPrijs().compareTo(BigDecimal.ONE) >= 0);
			assertTrue(pizza.getPrijs().compareTo(BigDecimal.TEN) <= 0);
			assertTrue(vorigePrijs.compareTo(pizza.getPrijs()) <= 0);
			vorigePrijs = pizza.getPrijs();
		}
		assertEquals(super.countRowsInTableWhere("pizzas", 
				                                 "prijs between 1 and 10"), 
				     pizzas.size());
	}

	@Test
	public void findUniekePrijzenGeeftPrijzenOplopend() {
		List<BigDecimal> prijzen = repository.findUniekePrijzen();
		int aantalPrijzen 
		        = super.jdbcTemplate.queryForObject("select count(distinct prijs) from pizzas", 
		        		                            Integer.class);
		assertEquals(aantalPrijzen, prijzen.size());
		BigDecimal vorigePrijs = BigDecimal.valueOf(-1);
		for (BigDecimal prijs : prijzen) {
			assertTrue(prijs.compareTo(vorigePrijs) > 0);
			vorigePrijs = prijs;
		}
	}

	//hulpmethode
	private int idVanTestPizza() {
		return super.jdbcTemplate.queryForObject
				  ("select id from pizzas where naam='test'", 
			       Integer.class);
	}
}
