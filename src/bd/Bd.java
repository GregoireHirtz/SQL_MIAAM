package bd;

import java.sql.*;

public class Bd {

    private Connection connection;

    public Bd(String url, String username, String password) throws SQLException{
        if (url==null || username==null || password==null) throw new NullPointerException("aucun paramètre ne doit être null");

        this.connection = DriverManager.getConnection(url, username, password);
    }

    /**
     * Exécute une requête SQL
     * @param query la requête
     * @return le résultat de la requête
     * @throws SQLException
     */
    public ResultSet executeQuery(String query) throws SQLException{
        if (query==null) throw new NullPointerException("la requête ne peut pas être null");

        Statement statement = this.connection.createStatement();
        return statement.executeQuery(query);
    }

    /**
     * Exécute une requête SQL avec des paramètres
     * @param query la requête
     * @param params les paramètres
     * @return le résultat de la requête
     * @throws SQLException
     */
    public ResultSet executeQuery(String query, Object... params) throws SQLException{
        if (query==null) throw new NullPointerException("la requête ne peut pas être null");

        PreparedStatement statement = this.connection.prepareStatement(query);
        for (int i = 0; i < params.length; i++) {
            statement.setObject(i+1, params[i]);
        }

        // si UPDATE, INSERT ou DELETE
        if (query.toUpperCase().startsWith("UPDATE") || query.toUpperCase().startsWith("INSERT") || query.toUpperCase().startsWith("DELETE")) {
            statement.executeUpdate();
            return null;
        }
        else {
            return statement.executeQuery();
        }
    }
}
