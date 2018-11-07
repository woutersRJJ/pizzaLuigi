package be.vdab.pizzaLuigi.web;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import be.vdab.pizzaLuigi.entities.Pizza;
import be.vdab.pizzaLuigi.services.DefaultPizzaService;
import be.vdab.pizzaLuigi.services.EuroService;

@Controller
@RequestMapping("pizzas")
class PizzaController {
	private final EuroService euroService;
	private final DefaultPizzaService pizzaService;
	
	private static final String REDIRECT_URL_NA_TOEVOEGEN="redirect:/pizzas";
	
	public PizzaController(EuroService euroService,
						   DefaultPizzaService pizzaService) {
		this.euroService=euroService;
		this.pizzaService=pizzaService;
	}
	
	@GetMapping
	ModelAndView pizzas() {
		ModelAndView modelAndView= new ModelAndView("pizzas","pizzas",pizzaService.findAll());
		return modelAndView;
	}
	
	@GetMapping("/prijzen")
	ModelAndView prijzen() {
		return new ModelAndView("prijzen","prijzen",pizzaService.findUniekePrijzen());
	}
	
	@GetMapping(params="prijs")
	ModelAndView pizzasVanPrijs(BigDecimal prijs) {
		ModelAndView modelAndView=new ModelAndView("prijzen", "pizzas",pizzaService.findByPrijs(prijs));
		modelAndView.addObject("prijs", prijs);
		modelAndView.addObject("prijzen", pizzaService.findUniekePrijzen());
		return modelAndView;
	}
	
	@GetMapping("{id}")
	ModelAndView pizza(@PathVariable int id) {
		ModelAndView modelAndView = new ModelAndView("pizza");
		Optional<Pizza>opt=pizzaService.read(id);
		if(opt.isPresent()) {
			Pizza pizza=opt.get();
			modelAndView.addObject(pizza);
			modelAndView.addObject("inDollar",euroService.naarDollar(pizza.getPrijs()));
		}
		return modelAndView;
	}
	
	@GetMapping("vantotprijs")
	ModelAndView findVanTotPrijs() {
		VanTotPrijsForm form=new VanTotPrijsForm();
		form.setVan(BigDecimal.ZERO);
		form.setTot(BigDecimal.ZERO);
		
		ModelAndView modelAndView=new ModelAndView("vantotprijs");
		modelAndView.addObject(form);
		return modelAndView;
	}
	
	//verwerk submit zoeken prijzen range
	@GetMapping(params = {"van" , "tot"})
	ModelAndView findVanTotPrijs (@Valid VanTotPrijsForm form,
								  BindingResult bindingResult){
		
		ModelAndView modelAndView=new ModelAndView("vantotprijs");
		
		if (bindingResult.hasErrors()) { 
			//geen pizzas doorgeven
			return modelAndView;
		}
		
		List<Pizza>pizzas
		     =pizzaService.findByPrijsBetween(form.getVan(),form.getTot());
		
		if(pizzas.isEmpty()) {
			bindingResult.reject("geenPizzas");
		}
		else {
			modelAndView.addObject("pizzas", pizzas);
		}
		return modelAndView;
	}
	
	@GetMapping("toevoegen")
		ModelAndView toevoegen() {
		return new ModelAndView("toevoegen").addObject(new Pizza()); 
	}
	
	//verwerk submit pizza toevoegen
	@PostMapping("toevoegen") 
	ModelAndView toevoegen(@Valid Pizza pizza, 
			                BindingResult bindingResult,
			                RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return new ModelAndView("toevoegen"); 
		}
		
		pizzaService.create(pizza); 
		
		//toon aanbod pizzas plus voorkom refresh probleem
		//boodschap meegeven aan redirect
		redirectAttributes.addAttribute("boodschap", "Pizza toegevoegd");
		return new ModelAndView(REDIRECT_URL_NA_TOEVOEGEN); 
	}	
}
