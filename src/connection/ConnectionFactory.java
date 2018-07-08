package connection;

import java.sql.*;

public class ConnectionFactory {

    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/controlx";
    private static final String USER = "root";
    private static final String PASS = "";

    public static Connection getConnection() throws ClassNotFoundException {

        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USER, PASS);

        } catch (SQLException e) {
            throw new RuntimeException("Erro na conexão: ", e);
        }

    }

    public static void closeConnection(Connection con) {
            try {
                if(con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public static void closeConnection(Connection con, PreparedStatement stmt) {

        closeConnection(con);

        try {

            if(stmt !=  null){
                stmt.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeConnection(Connection con, PreparedStatement stmt, ResultSet rs) {

        closeConnection(con, stmt);

        try {

            if(rs != null){
                rs.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
