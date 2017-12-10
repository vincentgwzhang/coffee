package tina.coffee.test.framework.easymock;

import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;

public class ExampleTest {


    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Test
    public void testcase1() {
        logger.info("[this is whole log message]");
    }

}