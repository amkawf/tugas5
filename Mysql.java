import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Mysql {
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private String url;
    private String username;
    private String password;
    private Connection conn;

    public Mysql(String hostname, String database, String port, String username, String password) {
        this.username = username;
        this.password = password;
        this.url = String.format("jdbc:mysql://%s:%s/%s", hostname, port, database);
    }

    public Connection connect() throws ClassNotFoundException, SQLException {
        Class.forName(Mysql.DRIVER);
        conn = DriverManager.getConnection(url, username, password);
        return conn;
    }
}