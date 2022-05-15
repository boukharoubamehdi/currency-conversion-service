package com.mb.rest.webservices.currencyconversionservice.conversion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
public class Controller {

  @Autowired CurrencyExchangeProxy currencyExchangeProxy;

  // For leaning purposes, we will leave
  // both methods (calculateConvertCurrencyRESTemplate & calculateConvertCurrencyFeign).
  // the method (calculateConvertCurrencyRESTemplate) is using direct call using REST Template and
  // the method (calculateConvertCurrencyFeign) is using direct call using Feign
  @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
  // http://localhost:8100/currency-conversion/from/USD/to/CAD/quantity/100
  public CurrencyConversion calculateConvertCurrencyRESTemplate(
      @PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {

    HashMap<String, String> uriVariable = new HashMap<>();
    uriVariable.put("from", from);
    uriVariable.put("to", to);

    // This is meant for learning purposes (no validation has been added)
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
        currencyConversion.getEnvironment() + " rest template");
  }

  @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
  // http://localhost:8100/currency-conversion-feign/from/USD/to/CAD/quantity/100
  public CurrencyConversion calculateConvertCurrencyFeign(
      @PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {

    // This is meant for learning purposes (no validation has been added)
    CurrencyConversion currencyConversion = currencyExchangeProxy.retrieveExchangeValue(from, to);

    return new CurrencyConversion(
        currencyConversion.getId(),
        from,
        to,
        quantity,
        currencyConversion.getConversionMultiple(),
        quantity.multiply(currencyConversion.getConversionMultiple()),
        currencyConversion.getEnvironment() + " feign");
  }
}
