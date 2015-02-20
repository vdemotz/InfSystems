package ch.ethz.globis.isk.config;

import org.h2.jdbcx.JdbcConnectionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Properties;


/**
 * The main configuration class for Spring.
 *
 * The @Configuration annotation marks it as a configuration class.
 * The @ComponentScan annotation marks the packages that will be scanned by Spring. Any Java classes
 * in these files that are annotated by @Component, @Service or @Repository will be instantiated automatically
 * by Spring. Moreover, any member attributes of objects corresponding to Spring managed classes and which are annotated
 * by @Autowired are automatically populated through dependency injection.
 * The @PropertySource annotation specifies a list of property files that Spring will scan for any properties.
 *
 */
@Configuration
@ComponentScan(basePackages = { "ch.ethz.globis.isk.persistence",
                                "ch.ethz.globis.isk.domain",
                                "ch.ethz.globis.isk.transaction",
                                "ch.ethz.globis.isk.service"})
@PropertySource({"jpa-persistence.properties"})
public class PersistenceConfig {

    /**
     * A reference to the Spring Environment. The Environment contains all the properties
     * in the property files listed as arguments to the @PropertySource annotation.
     *
     * Spring scans these files automatically once the annotation @PropertySource is set on a
     * class also marked with the @Configuration annotation.
     */
    @Autowired
    Environment environment;

    /**
     * A Boolean bean whose value determines if the database needs to be cleared on
     * startup.
     *
     * This is true in case of the profiles 'import' and 'test'.
     * @return                              True for the profiles 'import' and 'test'.
     */
    @Bean(name = "dropDatabase")
    @Profile({"import", "test"})
    Boolean dropDatabase() {
        return true;
    }

    /**
     * A Boolean bean whose value determines if the database needs to be cleared on
     * startup.
     *
     * This is false in case of the profile 'production'
     * @return                              False for the profiles ''production'.
     */
    @Bean(name = "dropDatabase")
    @Profile({"production"})
    Boolean productionDropDatabase() {
        return false;
    }

    /**
     * A String bean representing the name of the database to be used.
     *
     * The name is only used if the profile 'test' is active.
     * @return                              The name of the database.
     */
    @Bean(name = "databaseName")
    @Profile("test")
    String testDatabaseName() {
        return "dblp-test";
    }

    /**
     * A String bean representing the name of the database to be used.
     *
     * The name is only used if one of the profiles 'production' or 'import' is active.
     * @return                              The name of the database.
     */
    @Bean(name = "databaseName")
    @Profile({"production", "import"})
    String productionDatabaseName() {
        return "dblp";
    }

    /**
     * Create an H2 data source with the name received as an argument.
     *
     * Because this method is placed in a configuration file and that it is annotated
     * by @Bean, the databaseName String is a bean that Spring looks up in the application
     * context by the name 'databaseName'.
     *
     * @param databaseName                  The name of the database.
     *
     * @return                              A DataSource object configured to connected to
     *                                      the database with the name 'databaseName'.
     */
    @Bean
    public DataSource h2DataSource(String databaseName) {
        String databaseUrl = environment.getProperty("jdbc.url");
        String url = String.format(databaseUrl, databaseName);
        JdbcConnectionPool cp = JdbcConnectionPool.create(
                url,
                environment.getProperty("jdbc.user"),
                environment.getProperty("jdbc.pass"));
        return cp;
    }

    /**
     * Create a Properties object for the Hibernate properties.
     *
     * Because this method is placed in a configuration file and that it is annotated
     * by @Bean, the dropDatabase Boolean is a bean that Spring looks up in the application
     * context by the name 'dropDatabase'.
     *
     * @param dropDatabase                  Weather to drop the database or not.
     * @return                              A Properties bean that is used to configure Hibernate.
     */
    @Bean
    Properties hibernateProperties(Boolean dropDatabase) {
        final String hbm2ddAutoSetting = dropDatabase ? "create" : "update";

        return new Properties() {
            {
                setProperty("hibernate.dialect", environment.getProperty("hibernate.dialect"));
                setProperty("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
                setProperty("hibernate.hbm2ddl.auto", hbm2ddAutoSetting);
                setProperty("hibernate.jdbc.batch_size", environment.getProperty("hibernate.jdbc.batch_size"));
                setProperty("hibernate.order_inserts", environment.getProperty("hibernate.order_inserts"));
                setProperty("hibernate.order_updates", environment.getProperty("hibernate.order_updates"));
                setProperty("hibernate.cache.use_second_level_cache", environment.getProperty("hibernate.cache.use_second_level_cache"));
            }
        };
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
                                                                       JpaVendorAdapter jpaVendorAdapter,
                                                                       Properties hibernateProperties) {
        LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
        lef.setDataSource(dataSource);
        lef.setJpaVendorAdapter(jpaVendorAdapter);
        lef.setJpaProperties(hibernateProperties);
        lef.setPackagesToScan("ch.ethz.globis.isk.domain");
        return lef;
    }

    @Bean
    public EntityManager entityManager(LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean ) {
        return localContainerEntityManagerFactoryBean.getObject().createEntityManager();
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(true);
        hibernateJpaVendorAdapter.setGenerateDdl(false);
        hibernateJpaVendorAdapter.setDatabase(Database.HSQL);

        return hibernateJpaVendorAdapter;
    }
}