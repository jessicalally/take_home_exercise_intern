package com.shutl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shutl.Application;
import com.shutl.model.Quote;
import com.shutl.model.Vehicle;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class QuoteControllerFunctionalTest {

    @Autowired private WebApplicationContext webApplicationContext;

    ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;
    private final Quote defaultQuote = new Quote("SW1A1AA", "EC2A3LT", Vehicle.DEFAULT);

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.webApplicationContext).build();
    }

    private Quote performPostRequest(Quote quote) throws Exception {
        MvcResult result = this.mockMvc.perform(post("/quote")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(quote)))
            .andExpect(status().isOk())
            .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), Quote.class);
    }

    private void checkQuote(Quote quote, String pickupPostcode, String deliveryPostcode,
        Vehicle vehicle, long price) {
        assertEquals(quote.getPickupPostcode(), pickupPostcode);
        assertEquals(quote.getDeliveryPostcode(), deliveryPostcode);
        assertEquals(quote.getVehicle(), vehicle);
        assertEquals(quote.getPrice(), new Long(price));
    }

    @Test
    public void testBasicService() throws Exception {
        Quote quote = performPostRequest(defaultQuote);
        checkQuote(quote, "SW1A1AA", "EC2A3LT", Vehicle.DEFAULT, 316);
    }

    @Test
    public void testVariablePricingByDistance() throws Exception {
        Quote quote = performPostRequest(defaultQuote);
        checkQuote(quote, "SW1A1AA", "EC2A3LT", Vehicle.DEFAULT, 316);

        Quote newQuote = new Quote("AL15WD", "EC2A3LT", Vehicle.DEFAULT);
        newQuote = performPostRequest(newQuote);
        checkQuote(newQuote, "AL15WD", "EC2A3LT", Vehicle.DEFAULT, 305);
    }

    @Test
    public void testPricingOfBicycleDelivery() throws Exception {
        defaultQuote.setVehicle(Vehicle.BICYCLE);
        Quote quote = performPostRequest(defaultQuote);
        checkQuote(quote, "SW1A1AA", "EC2A3LT", Vehicle.BICYCLE, 348);
    }

    @Test
    public void testPricingOfMotorbikeDelivery() throws Exception {
        defaultQuote.setVehicle(Vehicle.MOTORBIKE);
        Quote quote = performPostRequest(defaultQuote);
        checkQuote(quote, "SW1A1AA", "EC2A3LT", Vehicle.MOTORBIKE, 363);
    }

    @Test
    public void testPricingOfParcelCarDelivery() throws Exception {
        defaultQuote.setVehicle(Vehicle.PARCEL_CAR);
        Quote quote = performPostRequest(defaultQuote);
        checkQuote(quote, "SW1A1AA", "EC2A3LT", Vehicle.PARCEL_CAR, 379);
    }

    @Test
    public void testPricingOfSmallVanDelivery() throws Exception {
        defaultQuote.setVehicle(Vehicle.SMALL_VAN);
        Quote quote = performPostRequest(defaultQuote);
        checkQuote(quote, "SW1A1AA", "EC2A3LT", Vehicle.SMALL_VAN, 411);
    }

    @Test
    public void testPricingOfLargeVanDelivery() throws Exception {
        defaultQuote.setVehicle(Vehicle.LARGE_VAN);
        Quote quote = performPostRequest(defaultQuote);
        checkQuote(quote, "SW1A1AA", "EC2A3LT", Vehicle.LARGE_VAN, 442);
    }

    @Test
    public void testAcceptsPostcodesContainingWhitespace() throws Exception {
        Quote quoteData = new Quote("SW1A 1AA", "EC2A 3LT", Vehicle.DEFAULT);
        Quote quote = performPostRequest(quoteData);;
        checkQuote(quote, "SW1A1AA", "EC2A3LT", Vehicle.DEFAULT, 316);
    }

    @Test
    public void testRejectsInvalidPostcode() throws Exception {
        Quote quoteData = new Quote("ABCDEFGH", "EC2A3LT", Vehicle.DEFAULT);
        this.mockMvc.perform(post("/quote")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(quoteData)))
            .andExpect(status().isBadRequest())
            .andReturn();
    }
}
