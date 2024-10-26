package me.mocadev.authserver.config;

import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import me.mocadev.authserver.entity.EntityModule;
import me.mocadev.authserver.repository.RepositoryModule;

/**
 * DB 설정
 *
 * @author mc.jeon
 * @version 1.0.0
 * @since 2024-10-26
 **/
@Configuration
@EntityScan(basePackageClasses = {EntityModule.class})
@EnableJpaRepositories(basePackageClasses = {RepositoryModule.class})
public class JpaConfig {

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new JpaTransactionManager();
	}

	@Bean
	public TransactionTemplate writeTransactionOperations(PlatformTransactionManager transactionManager) {
		var transactionTemplate = new TransactionTemplate(transactionManager);
		transactionTemplate.setReadOnly(false);
		return transactionTemplate;
	}

	@Bean
	public TransactionTemplate readTransactionOperations(PlatformTransactionManager transactionManager) {
		var transactionTemplate = new TransactionTemplate(transactionManager);
		transactionTemplate.setReadOnly(true);
		return transactionTemplate;
	}
}
