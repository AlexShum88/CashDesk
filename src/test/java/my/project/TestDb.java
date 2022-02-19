package my.project;

import my.project.services.db.DbCheckManager;
import org.junit.Test;
import static org.junit.Assert.assertEquals;



public class TestDb {
    DbCheckManager dbm = DbCheckManager.getInstance();
    int userId = 2;



    @Test
    public void testGetLastCheckByUserId(){
        for (int i = 0; i <= 12; i++) {

        }
        assertEquals((Integer)12, dbm.getCheckId(userId) );
    }
}