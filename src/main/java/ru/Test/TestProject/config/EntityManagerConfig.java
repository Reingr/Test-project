package ru.Test.TestProject.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

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
        sessionFactory.setPackagesToScan(new String[]{"ru.Test.TestProject.model"});
        sessionFactory.setHibernateProperties(hibernateProperties());
        sessionFactory.setPhysicalNamingStrategy(new SpringPhysicalNamingStrategy());
        sessionFactory.afterPropertiesSet();
        return sessionFactory.getObject();
    }

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        return modelMapper;
    }

    private Properties hibernateProperties() {
        final Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.dialect", environment.getProperty("spring.jpa.properties.hibernate.dialect"));
        hibernateProperties.put("hibernate.show_sql", environment.getProperty("spring.jpa.show-sql"));
        hibernateProperties.put("current_session_context_class", //
                environment.getProperty("spring.jpa.properties.hibernate.current_session_context_class"));
        hibernateProperties.put("hibernate.temp.use_jdbc_metadata_defaults", false);
        return hibernateProperties;
    }
}
