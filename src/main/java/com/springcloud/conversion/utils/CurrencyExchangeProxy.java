package com.springcloud.conversion.utils;

import com.springcloud.conversion.entities.CurrencyConversion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @PROJECT conversion-service
 * @Author Elimane on 14/07/2022
 */
//@FeignClient(name = "currency-exchange", url = "localhost:8002")
@FeignClient(name = "currency-exchange")
public interface CurrencyExchangeProxy {
  @GetMapping("/currency-exchange/from/{from}/to/{to}")
  public CurrencyConversion retrieveExchangeValue(@PathVariable String from, @PathVariable String to);
}
