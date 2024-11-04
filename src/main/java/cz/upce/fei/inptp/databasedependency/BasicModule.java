package cz.upce.fei.inptp.databasedependency;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import cz.upce.fei.inptp.databasedependency.dao.*;
import cz.upce.fei.inptp.databasedependency.entity.Person;
import cz.upce.fei.inptp.databasedependency.entity.PersonRole;

public class BasicModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Database.class).to(DatabaseImpl.class).in(Scopes.SINGLETON);
        bind(new TypeLiteral<DAO<Person>>() {
        }).to(PersonDAO.class);
        bind(new TypeLiteral<DAO<PersonRole>>() {
        }).to(PersonRolesDAO.class);
    }
}
