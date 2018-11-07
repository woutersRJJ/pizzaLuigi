package be.vdab.pizzaLuigi.web;

import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("identificatie")
class IdentificatieController {
	private static final String VIEW = "identificatie";
	private static final String REDIRECT_NA_SUBMIT = "redirect:/";
	
	private final Identificatie identificatie;

	IdentificatieController(Identificatie identificatie) {
		this.identificatie = identificatie;
	}

	@GetMapping
	ModelAndView identificatie() {
		return new ModelAndView(VIEW, "identificatie", identificatie);
	}

	//verwerken submit form
	@PostMapping
	ModelAndView identificatie(@Valid DefaultIdentificatie identificatie, 
			                          BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ModelAndView(VIEW);
		}
		this.identificatie.setEmailAdres(identificatie.getEmailAdres());
		
		return new ModelAndView(REDIRECT_NA_SUBMIT);
	}
}
