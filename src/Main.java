import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws SQLException {


        //TODO: get dbfile
        DbHandler dbHandlerInpower = new DbHandler("jdbc:sqlite:C:/prod/inpower.db");
        DbHandler dbHandlerCurse = new DbHandler("jdbc:sqlite:C:/prod/curse.db");

        //TODO: get tables
        ArrayList<String> tablesList = dbHandlerInpower.getDatabaseMetaData();

        //TODO: check table
        System.out.println(dbHandlerInpower.tableHasColumn("cat","filePostDate"));

        //TODO: if columns not exist add new fileId, state, filePostDate, blogName
        /*for (Map.Entry<String, String> entry : rowsAndDefinitions.entrySet()){
            dbHandler.addColumn("deer", entry.getKey() , entry.getValue());
        }*/

       //dbHandlerInpower.addColumn("catvideo","state" , "TEXT");

        //TODO: get Image from InPowerWeEntrust db

        Item item = dbHandlerInpower.returnImageFromInpower("cat","84561313_603525830379423_4602187154880098420_n");
        //System.out.println(item);


        //TODO: remove this item
        //dbHandlerInpower.deleteFileFromInpowerDB("cat",item.getPostId());

        //TODO: save image to CurseOfDeploy Db


        //TODO: get singe image from InPowerWeEntrust DB for testing purposes

        Item item666 = null;

        for (int i = 0; i < 10; i++){
            item666 = dbHandlerInpower.returnLastFileWithDateWhenItPostedFromInPowerDB("cat");
            dbHandlerCurse.addImageItemToCurseDB(item666);
            dbHandlerInpower.deleteFileFromInpowerDB(item666);
        }

    }
}
