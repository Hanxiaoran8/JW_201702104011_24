package cn.edu.sdjzu.xg.bysj.test;

import cn.edu.sdjzu.xg.bysj.dao.SchoolDao;
import cn.edu.sdjzu.xg.bysj.domain.School;

import java.sql.SQLException;

public class SchoolDaoTest {
    public static void main(String[] args) throws SQLException {
        School schoolToAdd = new School("XG","01","");
        SchoolDao schoolDao = new SchoolDao();
        School addedSchool = schoolDao.addWithSP(schoolToAdd);
        System.out.println(addedSchool);
        System.out.println("Finish adding School");
    }
}
