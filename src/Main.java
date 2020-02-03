import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws SQLException {

        HashMap<String,String> rowsAndDefinitions = new HashMap<>();
        rowsAndDefinitions.put("fileId", "integer PRIMARY KEY NOT NULL");
        rowsAndDefinitions.put("state", "TEXT");
        rowsAndDefinitions.put("filePostDate", "TIMESTAMP");



        //TODO: get dbfile
        DbHandler dbHandler = new DbHandler("jdbc:sqlite:C:/prod/myfin.db");

        //TODO: get tables
        ArrayList<String> tablesList = dbHandler.getDatabaseMetaData();

        //TODO: check table
        System.out.println(dbHandler.tableHasColumn("cat","filePostDate"));

        //TODO: if columns not exist add new fileId, state, filePostDate, blogName

        for (Map.Entry<String, String> entry : rowsAndDefinitions.entrySet()){
            dbHandler.addColumn("catvideo","state" , "TEXT");
        }

            dirs.add(new Blog(entry.getKey(), ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/getdirs/" + entry.getKey() + "/")
                    .toUriString(),"not date a gate"));

        dbHandler.addColumn("catvideo","state" , "TEXT");

    }
}
