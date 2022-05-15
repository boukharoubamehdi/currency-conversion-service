package com.mb.rest.webservices.currencyconversionservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class Controller {

  @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
  // http://localhost:8100/currency-converter/from/USD/to/CAD/quantity/10
  public CurrencyConversion convertCurrency(
      @PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {

    return new CurrencyConversion(1001L, from, to, quantity, BigDecimal.ONE, BigDecimal.TEN, "env");
  }
}
