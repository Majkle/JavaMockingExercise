package cz.upce.fei.inptp.databasedependency.business;

import cz.upce.fei.inptp.databasedependency.dao.PersonDAO;
import cz.upce.fei.inptp.databasedependency.dao.PersonRolesDAO;
import cz.upce.fei.inptp.databasedependency.entity.Person;
import cz.upce.fei.inptp.databasedependency.entity.PersonRole;
import cz.upce.fei.inptp.databasedependency.entity.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
class AuthorizationServiceTest {

    private static final String FULL_SECTION_PATH = "/section/subsection";
    private static final String ROOT_SECTION_PATH = "/section";
    private static final String ROOT_PATH = "/";

    private static final Person PERSON = new Person(0, "user", "password");
    protected static final String ADMIN = "admin";

    /**
     * Authorize(person, "/section/subsection", rw) - PersonRole([Role("/section/subsection", rw)]) - pass
     */
    @Test
    void testAuthorizeValidFullSectionWrite() {
        Assertions.assertTrue(createMockedAuthorizationService(FULL_SECTION_PATH, AccessOperationType.Write.getOp(), "")
                .authorize(PERSON, FULL_SECTION_PATH, AccessOperationType.Write));
    }

    /**
     * Authorize(person, "/section/subsection", rw) - PersonRole([Role("/section/subsection", ro)]) - fail
     */
    @Test
    void testAuthorizeInvalidFullSectionRead() {
        Assertions.assertFalse(createMockedAuthorizationService(FULL_SECTION_PATH, AccessOperationType.Read.getOp(), "")
                .authorize(PERSON, FULL_SECTION_PATH, AccessOperationType.Write));
    }

    /**
     * Authorize(person, "/section/subsection", rw) - PersonRole([Role("/section", rw)]) - pass
     */
    @Test
    void testAuthorizeValidRootSectionWrite() {
        Assertions.assertTrue(createMockedAuthorizationService(ROOT_SECTION_PATH, AccessOperationType.Write.getOp(), "")
                .authorize(PERSON, FULL_SECTION_PATH, AccessOperationType.Write));
    }

    /**
     * Authorize(person, "/section/subsection", rw) - PersonRole([Role("/section", ro)]) - fail
     */
    @Test
    void testAuthorizeInvalidRootSectionRead() {
        Assertions.assertFalse(createMockedAuthorizationService(ROOT_SECTION_PATH, AccessOperationType.Read.getOp(), "")
                .authorize(PERSON, FULL_SECTION_PATH, AccessOperationType.Write));
    }

    /**
     * Authorize(person, "/section/subsection", rw) - PersonRole([Role("/", rw)]) - pass
     */
    @Test
    void testAuthorizeInvalidRootWrite() {
        Assertions.assertTrue(createMockedAuthorizationService(ROOT_PATH, AccessOperationType.Write.getOp(), "")
                .authorize(PERSON, FULL_SECTION_PATH, AccessOperationType.Write));
    }

    /**
     * Authorize(person, "/section/subsection", rw) - PersonRole([Role("/", ro)]) - fail
     */
    @Test
    void testAuthorizeInvalidRootRead() {
        Assertions.assertFalse(createMockedAuthorizationService(ROOT_PATH, AccessOperationType.Read.getOp(), "")
                .authorize(PERSON, FULL_SECTION_PATH, AccessOperationType.Write));
    }

    /**
     * Authorize(person, "/section/subsection", rw) - PersonRole([Role("/section/subsection", admin)]) - pass
     */
    @Test
    void testAuthorizeInvalidFullSectionAdmin() {
        Assertions.assertTrue(createMockedAuthorizationService(FULL_SECTION_PATH, ADMIN, "")
                .authorize(PERSON, FULL_SECTION_PATH, AccessOperationType.Write));
    }

    /**
     * Authorize(person, "/section/subsection", rw) - PersonRole([Role("/section", admin)]) - pass
     */
    @Test
    void testAuthorizeInvalidRootSectionAdmin() {
        Assertions.assertTrue(createMockedAuthorizationService(ROOT_SECTION_PATH, ADMIN, "")
                .authorize(PERSON, FULL_SECTION_PATH, AccessOperationType.Write));
    }

    /**
     * Authorize(person, "/section/subsection", rw) - PersonRole([Role("/", admin)]) - pass
     */
    @Test
    void testAuthorizeInvalidRootAdmin() {
        Assertions.assertTrue(createMockedAuthorizationService(ROOT_PATH, ADMIN, "")
                .authorize(PERSON, FULL_SECTION_PATH, AccessOperationType.Write));
    }

    private AuthorizationService createMockedAuthorizationService(String section, String operationType, String modifier) {
        PersonRolesDAO personRolesDaoMock = Mockito.mock(PersonRolesDAO.class);

        PersonRole personRole = new PersonRole(PERSON,
                Arrays.asList(new Role(section, operationType, modifier)));

        Mockito.when(personRolesDaoMock.load("id = " + PERSON.getId())).thenReturn(personRole);

        return new AuthorizationService(new PersonDAO(), personRolesDaoMock);
    }
}