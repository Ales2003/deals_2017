package ru.mail.ales2003.deals2017.services;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:services-context.xml")
public abstract class AbstractTest {
	// TO DO: to need to extend in subclasses: insertTest(),
	// insertMultipleTest(), updateTest(), getTest(),
	// getAllTest(), deleteTest()
}