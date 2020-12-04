package com.ma.microservices.currencyconversionservice.controllers;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ma.microservices.currencyconversionservice.controllers.models.CurrencyConversionBean;
import com.ma.microservices.currencyconversionservice.feignservices.CurrencyExchangeServiceProxy;

@RestController
public class CurrencyConversionController {

	private static RestTemplate restTemplate = new RestTemplate();

	@Autowired
	private CurrencyExchangeServiceProxy currencyExchangeServiceProxy;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrency(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {

		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);

		ResponseEntity<CurrencyConversionBean> responseEntity = restTemplate.getForEntity(
				"http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversionBean.class,
				uriVariables);

		CurrencyConversionBean body = responseEntity.getBody();

		return new CurrencyConversionBean(body.getId(), body.getFrom(), body.getTo(), body.getConversionMultiple(),
				quantity, body.getConversionMultiple().multiply(quantity), body.getPort());
	}

	@GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrencyFeign(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {

		CurrencyConversionBean body = this.currencyExchangeServiceProxy.retrieveExchangeValue(from, to);
		
		logger.info("{}", body);
		return new CurrencyConversionBean(body.getId(), body.getFrom(), body.getTo(), body.getConversionMultiple(),
				quantity, body.getConversionMultiple().multiply(quantity), body.getPort());
	}
}
