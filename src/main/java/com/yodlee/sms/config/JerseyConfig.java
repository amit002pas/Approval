package com.yodlee.sms.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.stereotype.Component;

import com.yodlee.sms.controller.YApprovalControllerImpl;

@Component
public class JerseyConfig extends ResourceConfig{
	public JerseyConfig() {
		register(YApprovalControllerImpl.class);
		packages("{base.package}");
        property(ServletProperties.FILTER_FORWARD_ON_404, true);
	}
}
