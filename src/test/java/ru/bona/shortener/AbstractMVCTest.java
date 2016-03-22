package ru.bona.shortener;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;

/**
 * AbstractMVCTest
 *
 * @author Kontsur Alex (bona)
 * @since 25.11.14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath*:META-INF/spring/mvc-test-context.xml")
public abstract class AbstractMVCTest {

    protected MockMvc mock;
    protected Logger logger;

    @Inject
    private WebApplicationContext wac;

    @Before
    public void before() {
        logger = LoggerFactory.getLogger(getClass());
        mock = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @After
    public void after() {
    }

    protected ResultActions perform(RequestBuilder requestBuilder) throws Exception {
        return mock.perform(requestBuilder);
    }
}
