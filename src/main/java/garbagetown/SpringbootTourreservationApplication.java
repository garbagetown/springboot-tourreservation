package garbagetown;

import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.dozer.spring.DozerBeanMapperFactoryBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.terasoluna.gfw.common.sequencer.JdbcSequencer;
import org.terasoluna.gfw.common.sequencer.Sequencer;

import javax.inject.Inject;

@Slf4j
@SpringBootApplication
public class SpringbootTourreservationApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootTourreservationApplication.class, args);
    }
}
