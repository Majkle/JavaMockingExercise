package cz.upce.fei.inptp.databasedependency;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import cz.upce.fei.inptp.databasedependency.business.AuthorizationService;
import cz.upce.fei.inptp.databasedependency.business.AuthenticationService;
import cz.upce.fei.inptp.databasedependency.business.AccessOperationType;
import cz.upce.fei.inptp.databasedependency.dao.*;
import cz.upce.fei.inptp.databasedependency.entity.PersonRole;
import cz.upce.fei.inptp.databasedependency.entity.Person;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 */
public class Main {

    /*
    TODO: Tasks:
     - Create required unit tests for AuthenticationService
     - Create required unit tests for AuthorizationService
     - Create service UserManagerService with methods:
      - Service MUST depend only on DAO objects, no specific code for DB
      - CreateUser(String name, String password) : Person
      - DeleteUser(Person p) : boolean
      - ChangePassword(Person p, String newPassword) : boolean
     - Create service UserRoleManagerService
      - ...
    */
    public static void main(String[] args) throws SQLException {
        Injector injector = Guice.createInjector(new BasicModule());

//        Database database = new DatabaseImpl();
        Database database = injector.getInstance(Database.class);
        database.open();


//        PersonDAO personDao = new PersonDAO();
//        PersonDAO personDao = injector.getInstance(Key.get(PersonDAO.class, Names.named("person")));
        DAO<Person> personDao = injector.getInstance(PersonDAO.class);

        // create person
        Person person = new Person(10, "Peter", AuthenticationService.encryptPassword("rafanovsky"));
        personDao.save(person);

        // load person
        person = personDao.load("id = 10");
        System.out.println(person);

        // test authentication
//        AuthenticationService authentication = new AuthenticationService();
        AuthenticationService authentication = injector.getInstance(AuthenticationService.class);
        System.out.println(authentication.Authenticate("Peter", "rafa"));
        System.out.println(authentication.Authenticate("Peter", "rafanovsky"));

        // check user roles
//        PersonRolesDAO personRolesDAO = injector.getInstance(Key.get(PersonRolesDAO.class, Names.named("roles")));
        DAO<PersonRole> personRolesDAO = injector.getInstance(PersonRolesDAO.class);
        PersonRole pr = personRolesDAO.load("name = 'yui'");
//        PersonRole pr = new PersonRolesDAO().load("name = 'yui'");
        System.out.println(pr);

        // test authorization
        person = personDao.load("id = 2");
//        AuthorizationService authorization = new AuthorizationService();
        AuthorizationService authorization =  injector.getInstance(AuthorizationService.class);
        boolean authorizationResult = authorization.Authorize(person, "/finance/report", AccessOperationType.Read);
        System.out.println(authorizationResult);
        
        
        // load all persons from db
        try {
            Statement statement = database.createStatement();
            ResultSet rs = statement.executeQuery("select * from person");
            while (rs.next()) {
                System.out.println("name = " + rs.getString("name"));
                System.out.println("id = " + rs.getInt("id"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        
        database.close();
    }
}
