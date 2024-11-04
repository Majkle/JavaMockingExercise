package cz.upce.fei.inptp.databasedependency.dao;

import cz.upce.fei.inptp.databasedependency.business.AuthenticationService;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Database wrapper.
 */
public class DatabaseImpl implements Database {

    private Connection connection;

    public DatabaseImpl() {
    }

    @Override
    public void open() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
            seedDatabase();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void seedDatabase() throws SQLException {
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);  // set timeout to 30 sec.

        statement.executeUpdate("drop table if exists person");
        statement.executeUpdate("create table person (id integer, name string, password string)");

        statement.executeUpdate("insert into person values(1, 'leo', '" + AuthenticationService.encryptPassword("1234") + "')");
        statement.executeUpdate("insert into person values(2, 'yui', '" + AuthenticationService.encryptPassword("12345") + "')");
        statement.executeUpdate("insert into person values(3, 'johnny', '" + AuthenticationService.encryptPassword("xxx") + "')");

        statement.executeUpdate("drop table if exists role");
        statement.executeUpdate("create table role (id integer, section string, access string, modifier string)");

        statement.executeUpdate("insert into role values(1, '/', 'admin', '')");
        statement.executeUpdate("insert into role values(2, '/', 'user', '')");
        statement.executeUpdate("insert into role values(2, '/hr', 'rw', '')");
        statement.executeUpdate("insert into role values(2, '/finance', 'ro', '')");
        statement.executeUpdate("insert into role values(3, '/', 'user', '')");
        statement.executeUpdate("insert into role values(3, '/finance', 'rw', '')");
        statement.executeUpdate("insert into role values(3, '/finance/report', 'ro', '')");
    }

    @Override
    public Statement createStatement() throws SQLException {
        return connection.createStatement();
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

    @Override
    public void commit() throws SQLException {
        connection.commit();
    }

    @Override
    public void rollback() throws SQLException {
        connection.rollback();
    }

    @Override
    public void close() throws SQLException {
        connection.close();
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        return connection.createStatement(resultSetType, resultSetConcurrency);
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {
        connection.rollback(savepoint);
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return connection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
    }

}
