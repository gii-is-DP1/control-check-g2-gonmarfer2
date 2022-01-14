package org.springframework.samples.petclinic.feeding;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.Pet;
import org.springframework.samples.petclinic.pet.PetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FeedingController {
    
	private final String VIEW_CREATE_OR_UPDATE_FEEDING_FORM = "feedings/createOrUpdateFeedingForm";
	private PetService petService;
	private FeedingService feedingService;
	
	@Autowired
	public FeedingController(PetService petService, FeedingService feedingService) {
		this.petService = petService;
		this.feedingService = feedingService;
	}
	
	@GetMapping("/feeding/create")
	public String showFeedingForm(ModelMap model) {
		String view = VIEW_CREATE_OR_UPDATE_FEEDING_FORM;
		
		Feeding feeding = new Feeding();
		model.put("feeding", feeding);
		
		return view;
	}
	
	@PostMapping("feeding/create")
	public String postFeeding(@Valid Feeding feeding, BindingResult result, ModelMap model) {
		String view = VIEW_CREATE_OR_UPDATE_FEEDING_FORM;
		
		if(result.hasErrors()) {
			model.put("feeding", feeding);
			
		} else {
			try{
				this.feedingService.save(feeding);
				view = "redirect:/welcome";
			} catch(UnfeasibleFeedingException ufe) {
				 model.put("message", "La mascota seleccionada no se le puede asignar el plan de alimentación especificado.");
			}
			
		}
		
		return view;
	}
	
	@ModelAttribute("pets")
	public List<Pet> populatePetsInModel(){
		return this.petService.getAllPets();
	}
	
	@ModelAttribute("feedingTypes")
	public List<FeedingType> populateFeedingTypesInModel(){
		return this.feedingService.getAllFeedingTypes();
	}
	
	@InitBinder("feeding")
	public void initPetBinder(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
}
