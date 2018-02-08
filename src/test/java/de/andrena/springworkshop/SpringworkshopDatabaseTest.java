package de.andrena.springworkshop;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

// Much faster than the tests in SpringworkshopApplicationTests - @DataJpaTest causes only the configurations relevant to JPA to be applied
// Rolls back after each test so that they are independent of one another
@DataJpaTest
@RunWith(SpringRunner.class)
public class SpringworkshopDatabaseTest {

	@Autowired
	private TestEntityManager entityManager;
}
