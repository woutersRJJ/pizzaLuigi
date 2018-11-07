package be.vdab.pizzaLuigi.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import be.vdab.pizzaLuigi.entities.Pizza;
import be.vdab.pizzaLuigi.repositories.JdbcPizzaRepository;

@Service
@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
public class DefaultPizzaService implements PizzaService {

	private JdbcPizzaRepository repository;
	
	DefaultPizzaService(JdbcPizzaRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public int findAantalPizzas() {
		return repository.findAantalPizzas();
	}

	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	@Override
	public void create(Pizza pizza) {
		repository.create(pizza);
	}

	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	@Override
	public void update(Pizza pizza) {
		repository.update(pizza);
	}

	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	@Override
	public void delete(int id) {
		repository.delete(id);
	}

	@Override
	public Optional<Pizza> read(int id) {
		return repository.read(id);
	}

	@Override
	public List<Pizza> findAll() {
		return repository.findAll();
	}

	@Override
	public List<Pizza> findByPrijs(BigDecimal prijs) {
		return repository.findByPrijs(prijs);
	}

	@Override
	public List<Pizza> findByPrijsBetween(BigDecimal van, BigDecimal tot) {
		return repository.findByPrijsBetween(van, tot);
	}

	@Override
	public List<BigDecimal> findUniekePrijzen() {
		return repository.findUniekePrijzen();
	}
}
