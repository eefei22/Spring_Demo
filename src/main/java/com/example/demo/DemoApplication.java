package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner testDatabaseConnection(DataSource dataSource) {
		return args -> {
			try (Connection connection = dataSource.getConnection()) {
				System.out.println("Database connected successfully.");
				System.out.println("Database URL: " + connection.getMetaData().getURL());

				DatabaseMetaData metaData = connection.getMetaData();
				try (ResultSet tables = metaData.getTables(connection.getCatalog(), "demo_1", "%", new String[]{"TABLE"})) {
					System.out.println("Tables in the database:");
					while (tables.next()) {
						String tableName = tables.getString("TABLE_NAME");
						System.out.println("- " + tableName);
					}
				}

			} catch (SQLException e) {
				System.out.println("Database connection failed.");
				e.printStackTrace();
			}
		};
	}
}


