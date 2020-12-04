package com.ma.microservices.currencyexchangeservice.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ma.microservices.currencyexchangeservice.models.ExchangeValue;
import com.ma.microservices.currencyexchangeservice.repository.ExchangeValueRepository;

@RestController
public class CurrencyExchangeController {

	@Autowired
	Environment env;

	@Autowired
	ExchangeValueRepository exchangeValueRepository;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public ExchangeValue retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
		ExchangeValue exchangeValue = this.exchangeValueRepository.findByFromAndTo(from, to);
		exchangeValue.setPort(Integer.valueOf(env.getProperty("local.server.port")));
		logger.info("{}", exchangeValue);
		return exchangeValue;
	}

}
