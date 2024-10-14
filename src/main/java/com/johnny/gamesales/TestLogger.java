package com.johnny.gamesales;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLogger {
	
	private static final Logger logger = LoggerFactory.getLogger(TestLogger.class);
	
	public static void main(String[] args) {
		logger.info("hello,world");
		logger.error("ni shi shui");
		logger.debug("hello, xiaojie");
		logger.warn("hey,ljsdf");
	}
}
