import org.sqlite.JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbHandler {

    private static String CON_STR = null;
    private static DbHandler instance = null;

    public static synchronized DbHandler getInstance(String connectionString) throws SQLException {
        //CON_STR = connectionString;
        if (instance == null)
            instance = new DbHandler(connectionString);
        return instance;
    }

    private Connection connection;

    DbHandler(String connectionString) throws SQLException {
        CON_STR = connectionString;
        DriverManager.registerDriver(new JDBC());
        this.connection = DriverManager.getConnection(CON_STR);
    }

    public ArrayList<String> getDatabaseMetaData() {

        ArrayList<String> kok = new ArrayList<>();

        try {
            DatabaseMetaData dbmd = this.connection.getMetaData();
            String[] types = {"TABLE"};
            ResultSet rs = dbmd.getTables(null, null, "%", types);

            while (rs.next()) {
                System.out.println("tables: " + rs.getString("TABLE_NAME"));
                kok.add(rs.getString("TABLE_NAME"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return kok;
    }

    public boolean tableHasColumn(String tableName, String columnName) {

        try (Statement statement = this.connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("PRAGMA table_info(" + tableName + ")");

            while (resultSet.next()) {
                System.out.println("columns: " + resultSet.getString("name"));

                if (resultSet.getString("name").contains(columnName)){
                    return true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return false;
    }

    public void createTable(String tableName){
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (\n"
                + "	postId text NOT NULL UNIQUE,\n"
                + "	tags text NOT NULL,\n"
                + "	filenames text NOT NULL,\n"
                + " date TIMESTAMP NOT NULL);";

        try (Statement stmt = this.connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addColumn(String tableName, String columnName, String columnDefinition) {
        String sql = "ALTER TABLE " + tableName + " ADD " + columnName +  " " + columnDefinition + ";";
        try (Statement stmt = this.connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}


