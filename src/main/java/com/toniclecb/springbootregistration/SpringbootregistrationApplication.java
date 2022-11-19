package com.toniclecb.springbootregistration;

import java.sql.Types;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.toniclecb.springbootregistration.domain.entities.impl.CarCommom;

@SpringBootApplication
public class SpringbootRegistrationApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(SpringbootRegistrationApplication.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootRegistrationApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			log.info("************* Creating tables *************");

			jdbcTemplate.execute("DROP TABLE cars IF EXISTS");
			jdbcTemplate.execute("CREATE TABLE cars(id SERIAL, name VARCHAR(255), brand VARCHAR(255))");

			// "Golf Volkswagen", "Civic Honda", "Fiesta Ford", "Hilux Toyota"
			List<Object[]> carNames = Arrays.asList(
				new Object[]{"Golf", "Volkswagen"},
				new Object[]{"Civic", "Honda"},
				new Object[]{"Fiesta", "Ford"},
				new Object[]{"Hilux", "Toyota"}
			);

			carNames.forEach(name -> log.info(String.format("---> Inserting record: %s %s", name[0], name[1])));

			// Uses JdbcTemplate's batchUpdate operation to bulk load data
			jdbcTemplate.batchUpdate("INSERT INTO cars(name, brand) VALUES (?,?);", carNames);

			log.info("************* Querying cars where name = 'Civic' *************");
			List<Object> resultSetCars = jdbcTemplate.query(
				"SELECT id, name, brand FROM cars WHERE name = ?",
				new Object[] { "Civic" },
				new int[]{Types.VARCHAR},
				(rs, rowNum) -> new CarCommom(rs.getLong("id"), rs.getString("name"), rs.getString("brand"))
			);

			log.info("************* Result of query *********************");
			resultSetCars.forEach(car -> log.info(car.toString()));
		} catch (DataAccessException e) {
			log.error("Error while doing some database operation!", e);
		}
	}

}
