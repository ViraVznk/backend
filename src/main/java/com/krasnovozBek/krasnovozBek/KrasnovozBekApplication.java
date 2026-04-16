package com.krasnovozBek.krasnovozBek;

import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@Log
public class KrasnovozBekApplication implements CommandLineRunner {

//	private final DataSource dataSource;
//
//	KrasnovozBekApplication (DataSource dataSource) {
//		this.dataSource = dataSource;
//	}

	@Override
	public void run(String... args) throws Exception {
//		final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
//
//		//log.info("!!!!!"+dataSource.toString());
//		List<Map<String, Object>> categories = jdbcTemplate.queryForList("SELECT * FROM Category");
//		categories.forEach(row -> log.info("Category: " + row));
	}

	public static void main(String[] args) {
		SpringApplication.run(KrasnovozBekApplication.class, args);
	}

}
