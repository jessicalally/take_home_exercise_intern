package com.shutl.controller;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import com.shutl.model.Quote;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuoteController {
    @RequestMapping(value = "/quote", method = POST)
    public @ResponseBody Quote quote(@Valid @RequestBody Quote quote) {
        quote.setPrice(calculatePrice(quote));
        return quote;
    }

    private Long calculatePrice(Quote quote) {
        Double markup = quote.getVehicle().getMarkup();
        Long price = Math.abs((Long.valueOf(quote.getDeliveryPostcode(), 36) - Long.valueOf(quote.getPickupPostcode(), 36))/100000000);
        return Math.round(price * markup);
    }
}
