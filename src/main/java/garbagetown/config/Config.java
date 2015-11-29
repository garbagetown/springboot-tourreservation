package garbagetown.config;

import net.sf.log4jdbc.sql.jdbcapi.DataSourceSpy;
import org.dozer.DozerBeanMapper;
import org.h2.server.web.WebServlet;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.terasoluna.gfw.common.date.jodatime.DefaultJodaTimeDateFactory;
import org.terasoluna.gfw.common.date.jodatime.JodaTimeDateFactory;
import org.terasoluna.gfw.common.sequencer.JdbcSequencer;
import org.terasoluna.gfw.common.sequencer.Sequencer;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.Arrays;

/**
 * Created by garbagetown on 10/14/15.
 */
@Configuration
public class Config {

    @Inject
    DataSourceProperties dataSourceProperties;

    @Inject
    JdbcTemplate jdbcTemplate;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    DozerBeanMapper mapper() {
        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        dozerBeanMapper.setMappingFiles(Arrays.asList("dozer/domain-mapping.xml"));
        return dozerBeanMapper;
    }

    @Bean
    Sequencer<String> customerCodeSeq() {
        JdbcSequencer<String> sequencer = new JdbcSequencer<>();
        sequencer.setJdbcTemplate(jdbcTemplate);
        sequencer.setNextValueQuery("SELECT TO_CHAR(nextval('CUSTOMER_CODE_SEQ'),'FM00000000') AS customerCode");
        sequencer.setCurrentValueQuery("SELECT TO_CHAR(currval('CUSTOMER_CODE_SEQ'),'FM00000000') AS customerCode");
        sequencer.setSequenceClass(String.class);
        return sequencer;
    }

    @Bean
    Sequencer<String> reserveNoSeq() {
        JdbcSequencer<String> sequencer = new JdbcSequencer<>();
        sequencer.setJdbcTemplate(jdbcTemplate);
        sequencer.setNextValueQuery("SELECT TO_CHAR(nextval('RESERVE_NO_SEQ'),'FM00000000') AS reserveNo");
        sequencer.setCurrentValueQuery("SELECT TO_CHAR(currval('RESERVE_NO_SEQ'),'FM00000000') AS reserveNo");
        sequencer.setSequenceClass(String.class);
        return sequencer;
    }

    @Bean(name = "dateFactory")
    JodaTimeDateFactory jodaTimeDateFactory() {
        return new DefaultJodaTimeDateFactory();
    }

    @Bean(destroyMethod = "close")
    @ConfigurationProperties(prefix = DataSourceProperties.PREFIX)
    DataSource realDataSource() {
        DataSource dataSource = DataSourceBuilder
                .create(this.dataSourceProperties.getClassLoader())
                .url(this.dataSourceProperties.getUrl())
                .username(this.dataSourceProperties.getUsername())
                .password(this.dataSourceProperties.getPassword())
                .build();
        return dataSource;
    }

    @Bean
    @Primary
    DataSource dataSource() {
        return new DataSourceSpy(realDataSource());
    }

    @Bean
    public ServletRegistrationBean h2ServletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new WebServlet());
        registration.addUrlMappings("/console/*");
        return registration;
    }

}
