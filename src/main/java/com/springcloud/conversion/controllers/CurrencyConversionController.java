package com.springcloud.conversion.controllers;

import com.springcloud.conversion.entities.CurrencyConversion;
import com.springcloud.conversion.utils.CurrencyExchangeProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * @PROJECT conversion-service
 * @Author Elimane on 14/07/2022
 */
@RestController
//@RequestMapping(value = "/currency-conversion")
public class CurrencyConversionController {

  @Autowired
  private CurrencyExchangeProxy proxy;

  private Logger logger = LoggerFactory.getLogger(CurrencyConversionController.class);

  @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
  public CurrencyConversion calculate(@PathVariable String from,@PathVariable String to,@PathVariable BigDecimal quantity){

    logger.info("retrieveExchangeValue called with {} to {} with quantity of {}", from, to, quantity);

    //use Hashmap to make key/value data
    // including conversion parameters
    HashMap<String, String> uriVariables = new HashMap<>();
    uriVariables.put("from", from);
    uriVariables.put("to", to);

    //Connecting to invoke currency-exchange service
    ResponseEntity<CurrencyConversion> responseEntity =  new RestTemplate().getForEntity("http://localhost:8002/currency-exchange/from/"+ from+"/to/"+to, CurrencyConversion.class, uriVariables);
    CurrencyConversion currencyConversion = responseEntity.getBody();

    return new CurrencyConversion(currencyConversion.getId(), currencyConversion.getFrom(), currencyConversion.getTo(), quantity,currencyConversion.getConversionMultiple(),quantity.multiply(currencyConversion.getConversionMultiple()), currencyConversion.getEnvironment());
  }

  //Using FEIGN
  @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
  public CurrencyConversion calculateWithFeign(@PathVariable String from,@PathVariable String to,@PathVariable BigDecimal quantity){

    CurrencyConversion currencyConversion = proxy.retrieveExchangeValue(from,to);

    return new CurrencyConversion(currencyConversion.getId(), currencyConversion.getFrom(), currencyConversion.getTo(), quantity,currencyConversion.getConversionMultiple(),quantity.multiply(currencyConversion.getConversionMultiple()), currencyConversion.getEnvironment() +" "+"Feign");
  }

}
