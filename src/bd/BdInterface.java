package bd;

import java.sql.Connection;
import java.sql.SQLException;

public interface BdInterface {

    public Connection getConnection(String mail, String password) throws SQLException;
}
