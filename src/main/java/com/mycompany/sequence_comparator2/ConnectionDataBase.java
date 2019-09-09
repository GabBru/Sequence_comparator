package com.mycompany.sequence_comparator2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gabriel BRUNET & Clement SAURY
 */
public class ConnectionDataBase {

    private Connection con;

    private Statement stmt;

    public ConnectionDataBase() throws ClassNotFoundException, SQLException {
        // Load the driver class & create the connection object then create the statement of the object
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:8888/sequence_comparator", "root", "");
            stmt = con.createStatement();
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
