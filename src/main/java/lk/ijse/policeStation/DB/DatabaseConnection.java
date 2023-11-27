package lk.ijse.policeStation.DB;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection databaseConnection = null;
    private static Connection connection;

    public DatabaseConnection() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/policeStation",
                "root",
                "Ijse@1234"
        );
    }

    public static DatabaseConnection getInstance() throws SQLException, ClassNotFoundException {
        if (databaseConnection == null|| databaseConnection.getConnection().isClosed()) {
            databaseConnection = new DatabaseConnection();
        }
        return databaseConnection;
    }
    public static Connection getConnection(){
        return connection;
    }
}
