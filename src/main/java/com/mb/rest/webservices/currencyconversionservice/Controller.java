package com.mb.rest.webservices.currencyconversionservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
public class Controller {

  @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
  // http://localhost:8100/currency-converter/from/USD/to/CAD/quantity/100
  public CurrencyConversion convertCurrency(
      @PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {

    HashMap<String, String> uriVariable = new HashMap<>();
    uriVariable.put("from", from);
    uriVariable.put("to", to);

    //This is meant for learning purposes (no validation has been added)
    ResponseEntity<CurrencyConversion> responseEntity =
        new RestTemplate()
            .getForEntity(
                "http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                CurrencyConversion.class,
                uriVariable);

    CurrencyConversion currencyConversion = responseEntity.getBody();

    return new CurrencyConversion(
        currencyConversion.getId(),
        from,
        to,
        quantity,
        currencyConversion.getConversionMultiple(),
        quantity.multiply(currencyConversion.getConversionMultiple()),
        currencyConversion.getEnvironment());
  }
}
