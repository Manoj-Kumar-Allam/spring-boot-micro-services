package com.ma.microservices.netflixzuulapigatewayserver.filters;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class ZuulLoggingFilter extends ZuulFilter{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	// Should this filter should be executed or not based on some validations 
	@Override
	public boolean shouldFilter() {
		return true;
	}

	// The Main Logic
	@Override
	public Object run() throws ZuulException {
		
		HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
		
		logger.info("Request -> {}, Request URI -> {}", request, request.getRequestURI());
		return null;
	}

	// Decides when should be filtering happen before request (Pre), after request(post) or The error requests(error) requests that caused exception.
	@Override
	public String filterType() {
		return "pre";
	}

	// to set up priority order among the Zuul filters
	@Override
	public int filterOrder() {
		return 1;
	}

}
