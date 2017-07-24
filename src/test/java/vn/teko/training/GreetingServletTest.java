package vn.teko.training;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GreetingServletTest {
    @Before
    public void setUp() throws Exception {
        System.out.println("Setup GreetingServlet test");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Teardown GreetingServlet test");
    }

    @Test
    public void doGet() throws Exception {
        assertEquals(1 + 1, 2);
    }
}
