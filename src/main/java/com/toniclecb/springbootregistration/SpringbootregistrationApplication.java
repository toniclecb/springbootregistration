package com.toniclecb.springbootregistration;

import java.sql.Types;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import com.toniclecb.springbootregistration.domain.entities.impl.CarCommom;

@SpringBootApplication
public class SpringbootRegistrationApplication implements CommandLineRunner {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootRegistrationApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("************* Creating tables *************");

		jdbcTemplate.execute("DROP TABLE cars IF EXISTS");
		jdbcTemplate.execute("CREATE TABLE cars(id SERIAL, name VARCHAR(255), brand VARCHAR(255))");

		// "Golf Volkswagen", "Civic Honda", "Fiesta Ford", "Hilux Toyota"
		List<Object[]> carNames = Arrays.asList(
			new Object[]{"Golf", "Volkswagen"},
			new Object[]{"Civic", "Honda"},
			new Object[]{"Fiesta", "Ford"},
			new Object[]{"Hilux", "Toyota"}
		);

		carNames.forEach(name -> System.out.println(String.format("---> Inserting record: %s %s", name[0], name[1])));

		// Uses JdbcTemplate's batchUpdate operation to bulk load data
		jdbcTemplate.batchUpdate("INSERT INTO cars(name, brand) VALUES (?,?);", carNames);

		System.out.println("************* Querying cars where name = 'Civic' *************");
		List<Object> resultSetCars = jdbcTemplate.query(
			"SELECT id, name, brand FROM cars WHERE name = ?",
			new Object[] { "Civic" },
			new int[]{Types.VARCHAR},
			(rs, rowNum) -> new CarCommom(rs.getLong("id"), rs.getString("name"), rs.getString("brand"))
		);
		
		System.out.println("************* Result of query *********************");
		resultSetCars.forEach(car -> System.out.println(car.toString()));
	}

}
