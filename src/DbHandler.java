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

    public void deleteFileFromDB(int fileId, String tableName) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "DELETE FROM " + tableName + " WHERE fileId = ?")) {
            statement.setObject(1, fileId);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteFileFromInpowerDB(String tableName, String postId) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "DELETE FROM " + tableName + " WHERE postId = ?")) {
            statement.setObject(1, postId);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteFileFromInpowerDB(Item item) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "DELETE FROM " + item.getBlogName() + " WHERE postId = ?")) {
            statement.setObject(1, item.getPostId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Item returnImageFromInpower(String tableName, String fileName){

        try (Statement statement = this.connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName + " WHERE filenames LIKE '%"+ fileName +"%';");

            System.out.println("-------------------------------------------------");
            //return new FileWithDateWhenItPosted(resultSet.getInt("fileId"), resultSet.getString("fileName"), resultSet.getString("filePostDate"));
            while (resultSet.next()) {

                System.out.println("postId: " + resultSet.getString("postId"));
                System.out.println("tags: " + resultSet.getString("tags"));
                System.out.println("filenames: " + resultSet.getString("filenames"));
                System.out.println("date: " + resultSet.getString("date"));


                //    public Item(String blogName, String date, String postId, String tags, String filenames){
                return new Item(tableName, resultSet.getString("date"), resultSet.getString("postId"),resultSet.getString("tags"),resultSet.getString("filenames"));


            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return null;
    }

    private String prepareYourAnus(String stringToPrepareYourAnus){
        stringToPrepareYourAnus = stringToPrepareYourAnus.replace(".", "");
        stringToPrepareYourAnus = stringToPrepareYourAnus.replace("explore/tags/", "");
        return stringToPrepareYourAnus;
    }

    public void createTableInpower(String tableName){
        String sql = "CREATE TABLE IF NOT EXISTS " + this.prepareYourAnus(tableName) + " (\n" //private String blogName
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

    public void addImageItemToCurseDB(Item item) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "INSERT OR IGNORE INTO " + item.getBlogName() + "(`fileName`, `filePostDate`) " +
                        "VALUES(?, ?)")) {
            statement.setObject(1, "RRRR" + item.getFilenames());
            statement.setObject(2, item.getDate());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Item returnLastFileWithDateWhenItPostedFromInPowerDB(String tableName){

        try (Statement statement = this.connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName + " ORDER BY date ASC LIMIT 1");

            //System.out.println(resultSet.getString("fileName"));

            return new Item(tableName, resultSet.getString("date"), resultSet.getString("postId"),resultSet.getString("tags"),resultSet.getString("filenames"));


        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


}


