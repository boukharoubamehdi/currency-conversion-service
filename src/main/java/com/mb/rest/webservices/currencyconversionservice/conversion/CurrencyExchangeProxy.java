package com.mb.rest.webservices.currencyconversionservice.conversion;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Typically, we would use the application name of the service that we would want to call
// In our case the microService that we want to call has the property name:
// spring.application.name=currency-exchange
@FeignClient(name = "currency-exchange", url = "http://localhost:8000")
public interface CurrencyExchangeProxy {

  @GetMapping("/currency-exchange/from/{from}/to/{to}")
  CurrencyConversion retrieveExchangeValue(
      @PathVariable String from, @PathVariable String to);
}
