package no.api.candidate.webapp.dao;

import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/candidate-jdbc-test.xml"})
public class ATestDAOManager {

    @Autowired
    private SpringLiquibase liquibase;

    @Before
    public void setup() throws LiquibaseException {
        liquibase.afterPropertiesSet();
    }

}
