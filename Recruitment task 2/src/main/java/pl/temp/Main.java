package pl.temp;

import org.apache.log4j.BasicConfigurator;
import pl.temp.DTO.TempDTO;
import pl.temp.DAO.TempDAO;
import pl.temp.data.Data;

import java.sql.SQLException;
import java.sql.Timestamp;

public class Main {
    public static void main(String[] args) throws SQLException {
        BasicConfigurator.configure();

        TempDAO tempDAO = new TempDAO();
        tempDAO.createTableTemp();
        Data data = new Data();
        data.insertData();
        tempDAO.filterTamps();
    }
}
