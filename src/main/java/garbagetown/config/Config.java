package garbagetown.config;

import org.dozer.Mapper;
import org.dozer.spring.DozerBeanMapperFactoryBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.terasoluna.gfw.common.date.jodatime.DefaultJodaTimeDateFactory;
import org.terasoluna.gfw.common.date.jodatime.JodaTimeDateFactory;
import org.terasoluna.gfw.common.sequencer.JdbcSequencer;
import org.terasoluna.gfw.common.sequencer.Sequencer;

import javax.inject.Inject;

/**
 * Created by garbagetown on 10/14/15.
 */
@Configuration
public class Config {

    @Inject
    JdbcTemplate jdbcTemplate;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    FactoryBean<Mapper> mapper() throws Exception {
        return new DozerBeanMapperFactoryBean();
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

    @Bean(name = "dateFactory")
    JodaTimeDateFactory jodaTimeDateFactory() {
        return new DefaultJodaTimeDateFactory();
    }
}
