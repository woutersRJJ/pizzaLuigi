package be.vdab.pizzaLuigi.web;

import java.time.LocalTime;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
class IndexContoller {
	
	private final Identificatie identificatie;
	
	IndexContoller(Identificatie identificatie) {
		this.identificatie = identificatie;
	}
	
	@GetMapping
	ModelAndView index() {
		
		String boodschap="";
		int uur=LocalTime.now().getHour();
		if(uur<12) {
			boodschap="goede morgen";
		}
		else if(uur <18) {
			boodschap="goede middag";
		}
		else {
			boodschap="goede avond";
		}
		ModelAndView modelAndView=new ModelAndView("index");
		modelAndView.addObject("boodschap",boodschap);
		modelAndView.addObject("identificatie", identificatie);
		
		return modelAndView;
	}
}
