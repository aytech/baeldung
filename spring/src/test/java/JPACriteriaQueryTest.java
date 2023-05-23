import org.example.configuration.PersistenceConfig;
import org.example.entities.User;
import org.example.repositories.IUserDao;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class})
@Transactional
@TransactionConfiguration
public class JPACriteriaQueryTest {

  @Autowired
  private IUserDao userApi;

  @Before
  public void init() {
    User userJohn = new User();
    userJohn.setFirstName("John");
    userJohn.setLastName("Doe");
    userJohn.setEmail("john@doe.com");
    userJohn.setAge(22);
    userApi.save(userJohn);

    User userTom = new User();
    userTom.setFirstName("Tom");
    userTom.setLastName("Doe");
    userTom.setEmail("tom@doe.com");
    userTom.setAge(26);
    userApi.save(userTom);
  }
}