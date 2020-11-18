package ru.ITRev.TestProject.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;

@Configuration
public class EntityManagerConfig {
    private final Environment environment;

    @Autowired
    public EntityManagerConfig(Environment environment){
        this.environment = environment;
    }

    @Bean(name = "dataSource")
    public DataSource getDataSource() {
        try {
            HikariConfig config = new HikariConfig();
            config.setDriverClassName(environment.getProperty("spring.datasource.driver-class-name"));
            config.setJdbcUrl(environment.getProperty("spring.datasource.url"));
            config.setUsername(environment.getProperty("spring.datasource.username"));
            config.setPassword(environment.getProperty("spring.datasource.password"));
            return new HikariDataSource(config);
        } catch (Exception ex) {
            System.exit(1);
            return null;
        }
    }

    @Bean(name = "entityManagerFactory")
    public SessionFactory getSessionFactory() throws Exception {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(getDataSource());
        sessionFactory.setPackagesToScan(new String[]{"ru.ITRev.TestProject.model"});
        sessionFactory.setPhysicalNamingStrategy(new SpringPhysicalNamingStrategy());
        sessionFactory.afterPropertiesSet();
        return sessionFactory.getObject();
    }
}
