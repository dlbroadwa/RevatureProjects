import auction.dataaccess.UserDAO;
import auction.models.User;
import auction.services.UserService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

public class ServiceTest {

    @Mock
    private UserDAO userDAO = null;
    @Mock
    private UserService userService = null;
    private User user = null;
    private User actualUser = null;
    @Before
    public void init() {
        userDAO = new UserDAO();
        userService = new UserService(userDAO);
        user = new User("randomusername", "password");
        actualUser = new User("Greg Cox", "password3");
    }


    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testDeleteShouldFail(){
        assertFalse(userService.deleteUser(user));
    }

    @Test
    public void testDeleteShouldSucceed(){
        assertTrue(userService.deleteUser(actualUser));
    }

    @Test
    public void testRetrieveAll(){
        List<User> users;
        users = userService.retrieveAll();
        for (User listUser: users) {
            System.out.println(listUser);
        }
        assertNotNull(users);
    }

    @Test
    public void testRetrieve() {
        assertNotNull(userService.retrieveByName("Greg Cox"));
    }

}
