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

        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(
                "jdbc:mysql://remotemysql.com:3306/jeho8etZte", "jeho8etZte", "GxTGVa1AvL");
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
