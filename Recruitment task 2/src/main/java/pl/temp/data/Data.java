package pl.temp.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.temp.DAO.TempDAO;
import pl.temp.connection.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Data {

    public void insertData() throws SQLException {
        Logger logger = LoggerFactory.getLogger(TempDAO.class);
        final ConnectionFactory connectionFactory = new ConnectionFactory();
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("CREATE PROCEDURE proc_temp(\n" +
                     "  IN time TIMESTAMP,\n" +
                     "  IN amount INT\n" +
                     "  )\n" +
                     "BEGIN\n" +
                     "  DECLARE i INT DEFAULT 0;\n" +
                     "  DECLARE new_date TIMESTAMP;\n" +
                     "  DECLARE date_hour INT;\n" +
                     "  WHILE i < amount DO\n" +
                     "      SET new_date = DATE_ADD(time, INTERVAL i MINUTE);\n" +
                     "      SET date_hour = HOUR(new_date);\n" +
                     "      INSERT INTO temp (measurement, time) VALUES (IF(date_hour < 6 OR date_hour > 17, NULL, ROUND(RAND()*(25-10)+10, 2)), new_date);\n" +
                     "      SET i = i + 1;\n" +
                     "  END WHILE;\n" +
                     "END");
             PreparedStatement day1 = connection.prepareStatement("call proc_temp('2021-01-06 00:00:00'," + 24 * 60 + ");");
             PreparedStatement day2 = connection.prepareStatement("call proc_temp('2021-01-07 00:00:00'," + 24 * 60 + ");");
             PreparedStatement day3 = connection.prepareStatement("call proc_temp('2021-01-08 00:00:00'," + 24 * 60 + ");");
        ) {
            statement.execute();
            day1.execute();
            day2.execute();
            day3.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
