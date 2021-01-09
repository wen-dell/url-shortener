package br.com.urlshortener.dataSource;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = {
		"br.com.urlshortener.domain.repository" }, entityManagerFactoryRef = "EntityManagerFactory", transactionManagerRef = "TransactionManager")
public class DataSourceConfiguration {

	private String[] packagesToScan = getPackagesToScan("model");

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.urlshortener")
	public DataSourceProperties DataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	public DataSource DataSource() {
		DataSourceProperties DataSourceProperties = DataSourceProperties();
		return DataSourceBuilder.create().driverClassName(DataSourceProperties.getDriverClassName())
				.url(DataSourceProperties.getUrl()).username(DataSourceProperties.getUsername())
				.password(DataSourceProperties.getPassword()).build();
	}

	@Bean
	public PlatformTransactionManager TransactionManager() {
		EntityManagerFactory Factory = EntityManagerFactory().getObject();
		return new JpaTransactionManager(Factory);
	}

	@Bean
	@DependsOn("flyway")
	public LocalContainerEntityManagerFactoryBean EntityManagerFactory() {
		LocalContainerEntityManagerFactoryBean Factory = new LocalContainerEntityManagerFactoryBean();
		Factory.setDataSource(DataSource());
		Factory.setPackagesToScan(packagesToScan);
		Factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

		return Factory;
	}

	private static String[] getPackagesToScan(String... schemas) {
		final String preffix = "br.com.urlshortener.domain.";
		String[] packages = new String[schemas.length];

		for (int i = 0; i < schemas.length; i++) {
			packages[i] = preffix.concat(schemas[i]);
		}

		return packages;

	}

	@Bean(initMethod = "migrate")
	public Flyway flyway() {
		return Flyway.configure().dataSource(DataSource()).load();

	}

}
