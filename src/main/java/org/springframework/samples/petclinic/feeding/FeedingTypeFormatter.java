package org.springframework.samples.petclinic.feeding;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class FeedingTypeFormatter implements Formatter<FeedingType>{

	private FeedingService feedingService;
	
	@Autowired
	public FeedingTypeFormatter(FeedingService feedingService) {
		this.feedingService = feedingService;
	}
	
    @Override
    public String print(FeedingType object, Locale locale) {
        return object.getName();
    }

    @Override
    public FeedingType parse(String text, Locale locale) throws ParseException {
    	FeedingType ft = this.feedingService.getFeedingType(text);
    	//La consulta tiene el parámetro con @Nullable, devolverá nulo si no existe.
    	if(ft == null) {
    		throw new ParseException("El tipo FeedingType (" + 
    				text + ") no se encuentra en la base de datos", 0);
    	}
    	
    	return ft;
    }
    
}
