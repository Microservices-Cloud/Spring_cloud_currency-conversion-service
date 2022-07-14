package com.springcloud.conversion.controllers;

import com.springcloud.conversion.entities.CurrencyConversion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @PROJECT conversion-service
 * @Author Elimane on 14/07/2022
 */
@RestController
@RequestMapping(value = "/currency-conversion")
public class CurrencyConversionController {

  @GetMapping("/from/{from}/to/{to}/quantity/{quantity}")
  public CurrencyConversion calculate(@PathVariable String from,@PathVariable String to,@PathVariable BigDecimal quantity){
    return new CurrencyConversion(1001L, from, to, quantity,BigDecimal.ONE,BigDecimal.ONE, "8000 instance-id");
  }
}
