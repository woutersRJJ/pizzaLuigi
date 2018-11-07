package be.vdab.pizzaLuigi.repositories;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import be.vdab.pizzaLuigi.entities.Pizza;
import be.vdab.pizzaLuigi.exceptions.PizzaNietGevondenException;

@Repository
public class JdbcPizzaRepository implements PizzaRepository {
	private final JdbcTemplate template;
	private final SimpleJdbcInsert insert;
	private final RowMapper<Pizza> pizzaRowMapper 
		= (resultSet, rowNum) ->
				new Pizza(resultSet.getInt("id"), 
						  resultSet.getString("naam"),
						  resultSet.getBigDecimal("prijs"),
						  resultSet.getBoolean("pikant"));
	private final RowMapper<BigDecimal> prijsRowMapper
		= (resultSet, rowNum) -> 
	              resultSet.getBigDecimal("prijs");
								
	public JdbcPizzaRepository(JdbcTemplate template) {
		this.template = template;
		insert=new SimpleJdbcInsert(template);
		insert.withTableName("pizzas");
		insert.usingGeneratedKeyColumns("id");
	}

	@Override
	public int findAantalPizzas() {
		int aant=template.queryForObject("select count(*) from pizzas", Integer.class);
		return aant;
	}
	
	@Override
	public void create(Pizza pizza) {
		Map<String,Object>kolomwaarden=new HashMap<>();
		kolomwaarden.put("naam", pizza.getNaam());
		kolomwaarden.put("prijs", pizza.getPrijs());
		kolomwaarden.put("pikant", pizza.isPikant());
		Number id=insert.executeAndReturnKey(kolomwaarden);
		pizza.setId(id.intValue());	
	}

	@Override
	public void update(Pizza pizza) {
		String sql="update pizzas set naam=?, prijs=?, pikant=? where id=?";
		int aant=template.update(sql,
				                pizza.getNaam(), 
				                pizza.getPrijs(),
				                pizza.isPikant(), 
				                pizza.getId());
		if(aant==0) {
			throw new PizzaNietGevondenException("foutboodschap");
		}	
	}

	@Override
	public void delete(int id) {
		template.update("delete from pizzas where id=?", id);
	}

	@Override
	public Optional<Pizza> read(int id) {
		String sql="select id, naam, prijs, pikant from pizzas where id=?";
		try {
			return Optional.of(template.queryForObject(sql, 
					                                   pizzaRowMapper, 
					                                   id));
			} 
		catch (IncorrectResultSizeDataAccessException ex) {
			return Optional.empty();
		}
	}

	@Override
	public List<Pizza> findAll() {
		return template.query("select id, naam, prijs, pikant from pizzas order by id", 
				              pizzaRowMapper);
	}

	@Override
	public List<Pizza> findByPrijs(BigDecimal prijs) {
		return template.query("select id, naam, prijs, pikant from pizzas where prijs=? order by naam", 
							  pizzaRowMapper, prijs);
	}

	@Override
	public List<Pizza> findByPrijsBetween(BigDecimal van, BigDecimal tot) {
		String sql="select id, naam, prijs, pikant from pizzas" + 
		           " where prijs between ? and ? order by prijs";
		return template.query(sql, pizzaRowMapper, van, tot);
	}

	@Override
	public List<BigDecimal> findUniekePrijzen() {
		return template.query("select distinct prijs from pizzas order by prijs", 
				              prijsRowMapper);
	}	
}
