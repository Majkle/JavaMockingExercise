package cz.upce.fei.inptp.databasedependency.business;

import cz.upce.fei.inptp.databasedependency.dao.PersonDAO;
import cz.upce.fei.inptp.databasedependency.entity.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    private static final String VALID_PASSWORD = "password";
    private static final Person VALID_PERSON = new Person(0, "user", AuthenticationService.encryptPassword(VALID_PASSWORD));

    private static AuthenticationService authenticationService;

    @BeforeAll
    static void beforeAll() {
        PersonDAO personDaoMock = Mockito.mock(PersonDAO.class);

        Mockito.when(personDaoMock.load("name = '" + VALID_PERSON.getName() + "'")).thenReturn(VALID_PERSON);

        authenticationService = new AuthenticationService(personDaoMock);
    }

    /**
     * Authenticate("user", "pass") - Person("user", encryptPwd("pass")) - pass
     */
    @Test
    void testAuthenticateHds() {
        Assertions.assertTrue(authenticationService.authenticate(VALID_PERSON.getName(), VALID_PASSWORD));
    }

    /**
     * Authenticate("user", "invalid") - Person("user", encryptPwd("pass")) - fail
     */
    @Test
    void testAuthenticateInvalidPassword() {
        Assertions.assertFalse(authenticationService.authenticate(VALID_PERSON.getName(), "pepa"));
    }

    /**
     * Authenticate("user", "pass") - nonexistent person - fail
     */
    @Test
    void testAuthenticateInvalidUser() {
        Assertions.assertFalse(authenticationService.authenticate("zdepa", VALID_PASSWORD));
    }
}