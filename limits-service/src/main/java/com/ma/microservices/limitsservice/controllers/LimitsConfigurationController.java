package com.ma.microservices.limitsservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ma.microservices.limitsservice.config.LimitServiceConfiguration;
import com.ma.microservices.limitsservice.models.LimitConfiguration;

@RestController
public class LimitsConfigurationController {

	private final LimitServiceConfiguration limitServiceConfiguration;

	public LimitsConfigurationController(LimitServiceConfiguration limitServiceConfiguration) {
		this.limitServiceConfiguration = limitServiceConfiguration;
	}
	
	@GetMapping("/getLimitsConfigurations")
	public LimitConfiguration retrieveLimitsFromConfigurations() {
		return new LimitConfiguration(this.limitServiceConfiguration.getMinimum(), this.limitServiceConfiguration.getMaximum());
	}
}
