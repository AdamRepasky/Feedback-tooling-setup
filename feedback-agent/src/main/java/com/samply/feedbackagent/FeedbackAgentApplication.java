package com.samply.feedbackagent;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FeedbackAgentApplication {

	@Autowired
	private ProxyRequestPoller proxyRequestPoller;

	public static void main(String[] args) {
		SpringApplication.run(FeedbackAgentApplication.class, args);
	}

	@PostConstruct
	public void startProxyRequestPoller() {
		proxyRequestPoller.start();
	}
}
