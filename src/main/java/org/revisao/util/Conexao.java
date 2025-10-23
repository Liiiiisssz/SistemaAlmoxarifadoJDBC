package org.revisao.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String url = "jdbc:mysql://localhost:3306/almoxarifado?useSSL=false&serverTimezone=UTC&allowMultiQueries=true";
    private static final String user = "root";
    private static final String password = "mysqlPW";

    public static Connection conectar() throws SQLException{
        return DriverManager.getConnection(url, user, password);
    }
}
