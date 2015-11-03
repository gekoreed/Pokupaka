import handlersTest.HandShakeTest;
import handlersTest.RegistrationTest;
import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * By gekoreed on 10/4/15.
 */
@RunWith(Categories.class)
@Suite.SuiteClasses( { HandShakeTest.class, RegistrationTest.class })
public class Runner {
}
