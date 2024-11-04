package cz.upce.fei.inptp.databasedependency.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

public interface Database {

    void open();

    Statement createStatement() throws SQLException;

    PreparedStatement prepareStatement(String sql) throws SQLException;

    void commit() throws SQLException;

    void rollback() throws SQLException;

    void close() throws SQLException;

    Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException;

    void rollback(Savepoint savepoint) throws SQLException;

    Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException;
}
