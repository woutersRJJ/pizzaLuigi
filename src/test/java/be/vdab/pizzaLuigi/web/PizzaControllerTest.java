package be.vdab.pizzaLuigi.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;
import be.vdab.pizzaLuigi.entities.Pizza;
import be.vdab.pizzaLuigi.services.DefaultPizzaService;
import be.vdab.pizzaLuigi.services.EuroService;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PizzaControllerTest {

	private PizzaController controller;
	@Mock private EuroService dummyEuroService;
	@Mock private DefaultPizzaService dummyPizzaService;
	
	@Before
	public void before() {
		controller = new PizzaController(dummyEuroService, dummyPizzaService);
		when(dummyPizzaService.read(1)).thenReturn(Optional.of(new Pizza(1, "test", BigDecimal.ONE, true)));
	}
	
	@Test
	public void pizzasWerktSamenMetDeJspPizzas() {
		ModelAndView modelAndView=controller.pizzas();
		assertEquals("pizzas",modelAndView.getViewName());
	}

	@Test
	public void pizzaWerktSamenMetDeJspPizza() {
		ModelAndView modelAndView = controller.pizza(1);
		assertEquals("pizza", modelAndView.getViewName());
	}
	
	@Test
	public void pizzasGeeftPizzasDoor() {
		ModelAndView modelAndView = controller.pizzas();
		assertTrue(modelAndView.getModel().get("pizzas") instanceof List);
	}
	
	@Test
	public void pizzaGeeftPizzaDoor() {
		ModelAndView modelAndView = controller.pizza(1);
		assertTrue(modelAndView.getModel().get("pizza") instanceof Pizza);
	}
	
	@Test
	public void onbestaandePizza() {
		ModelAndView modelAndView = controller.pizza(-1);
		assertFalse(modelAndView.getModel().containsKey("pizza"));
	}
}
