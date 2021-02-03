package pl.temp.DAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.temp.DTO.TempDTO;
import pl.temp.connection.ConnectionFactory;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class TempDAO {
    Logger logger = LoggerFactory.getLogger(TempDAO.class);
    private final ConnectionFactory connectionFactory = new ConnectionFactory();

    public TempDAO() throws SQLException {
    }

    public void createTableTemp() {

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("CREATE TABLE `temp` (\n" +
                     "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                     "  `time` TIMESTAMP NOT NULL,\n" +
                     "  `measurement` DOUBLE,\n" +
                     "  PRIMARY KEY (`id`))")) {
            final DatabaseMetaData metaData = connection.getMetaData();
            final ResultSet temps = metaData.getTables(null, null, "temp", null);
            if (temps.next()) {
                logger.info("Table temp exists");
            } else {
                statement.execute();
                logger.info("Table temp created!!!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private List<TempDTO> getAllTemps() throws SQLException {
        List<TempDTO> temps = new ArrayList<>();
        String query = "SELECT * FROM temp";

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            final ResultSet resultSetTemp = statement.executeQuery();
            while (resultSetTemp.next()) {
                final Timestamp time = resultSetTemp.getTimestamp("time");
                final Object measurement = resultSetTemp.getObject("measurement");
                final int tempId = resultSetTemp.getInt("id");
                final TempDTO tempDTO = new TempDTO();
                tempDTO.setMeasurement((Double) measurement);
                tempDTO.setTime(time);
                tempDTO.setId(tempId);
                temps.add(tempDTO);
            }
        }
        return temps;
    }


    public void filterTamps() throws SQLException {
        final List<TempDTO> tempDTOS = getAllTemps();
        final Map<Integer, TempDTO> sunrise = tempDTOS.stream()
                .filter(e -> e.getMeasurement() != null)
                .collect(Collectors.groupingBy(element -> element.getTime().getDay(),
                        Collectors.collectingAndThen(Collectors.reducing((TempDTO d1, TempDTO d2) ->
                                        d1.getTime().before(d2.getTime()) ? d1 : d2),
                                Optional::get)));

        final Map<Integer, TempDTO> sunset = tempDTOS.stream()
                .filter(e -> e.getMeasurement() != null)
                .collect(Collectors.groupingBy(element -> element.getTime().getDay(),
                        Collectors.collectingAndThen(Collectors.reducing((TempDTO d1, TempDTO d2) ->
                                        d1.getTime().after(d2.getTime()) ? d1 : d2),
                                Optional::get)));
        sunrise.forEach((key, value) -> System.out.println("Sunrise:" + value.getTime()));

        sunset.forEach((key, value) -> System.out.println("Sunset:" + value.getTime()));
    }


    public void createTemp(TempDTO temp) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO temp(measurement, time) VALUES(?,?) ");
             PreparedStatement statementIsNullMeasurement = connection.prepareStatement("INSERT INTO temp(time) VALUES(?) ")) {
            if (temp.getMeasurement() != null) {
                statement.setDouble(1, temp.getMeasurement());
                statement.setTimestamp(2, temp.getTime());
                statement.execute();
            } else {
                statementIsNullMeasurement.setTimestamp(1, temp.getTime());
                statementIsNullMeasurement.execute();
            }
            logger.info("Created new temp " + temp);


        } catch (SQLException e) {
            logger.error("Can't create temp " + temp);
            e.printStackTrace();
        }

    }


}
