package com.mycompany.sequence_comparator2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Gabriel BRUNET & Clement SAURY
 */
public class ConnectionDataBase {

    private final Connection con;

    private Statement stmt;

    public ConnectionDataBase() throws ClassNotFoundException, SQLException {
        // Load the driver class & create the connection object then create the statement of the object
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.con = DriverManager.getConnection(
                "jdbc:mysql://localhost:8889/sequence_comparator?zeroDateTimeBehavior=convertToNull", "root", "root");
        this.stmt = con.createStatement();
    }

    public void shutdown(Connection con) throws SQLException {
        if (con != null) {
            con.close();
        }
    }

    public Connection getCon() {
        return con;
    }

    public Statement getStatement() {
        return stmt;
    }

    public void setStmt(Statement stmt) {
        this.stmt = stmt;
    }

    public Statement getStmt() {
        return stmt;
    }
}
