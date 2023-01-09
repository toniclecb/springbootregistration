package com.toniclecb.springbootregistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.toniclecb.springbootregistration.interfaces.DateTime;

@SpringBootApplication
public class SpringbootRegistrationApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(SpringbootRegistrationApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringbootRegistrationApplication.class, args);
	}

	@Bean
	public DateTime getDateTime(){
		return new DateTimeImpl();
	}

	@Override
	public void run(String... args) throws Exception {
		testLogging();
	}

	/**
	 * First we test the different levels of log.
	 * Then we test the SizeAndTimeBasedRollingPolicy to verify if files of 1MB will be created.
	 */
	private void testLogging() {
		// log message // log level
		log.info("Initial INFO message log"); // info debug
		log.trace("Initial TRACE message log"); //
		log.debug("Initial DEBUG message log"); // debug
		log.warn("Initial WARN message log"); // info debug
		log.error("Initial ERROR message log"); // info debug error

		/*
		 * let's comment this section for now, we have already test this!
		for (int i = 0; i < 50000; i++) {
			// this log section must create 3 files of log (1mb each!)
			log.info("This is a message of test for logging proposals, file must cut in 1MB");
		}
		*/
	}

}
