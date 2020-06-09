package company.connections;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class MyConnection {
    protected String url;
    protected String username;
    protected String password;
    protected String schema ;

    public abstract Connection getConnection() throws SQLException; //declare this

}
