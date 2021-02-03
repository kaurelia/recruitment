package pl.temp.connection;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private static Logger logger = LoggerFactory.getLogger(ConnectionFactory.class);
    private static DataSource dataSource;

    public ConnectionFactory() throws SQLException {
        Properties databaseProperties = getDatabaseProperties("/database.properties");
        dataSource = getDataSource(databaseProperties);
    }

    private DataSource getDataSource(Properties databaseProperties) throws SQLException {
        final String name = databaseProperties.getProperty("name");
        final String server = databaseProperties.getProperty("server");
        final String user = databaseProperties.getProperty("user");
        final String password = databaseProperties.getProperty("password");
        final Integer port = Integer.parseInt(databaseProperties.getProperty("port"));
        MysqlDataSource mysqlDataSource = new MysqlDataSource();

        mysqlDataSource.setServerName(server);
        mysqlDataSource.setDatabaseName(name);
        mysqlDataSource.setUser(user);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setPort(port);
        mysqlDataSource.setServerTimezone("Europe/Warsaw");
        mysqlDataSource.setUseSSL(false);
        mysqlDataSource.setCharacterEncoding("UTF-8");
        return mysqlDataSource;


    }


    private Properties getDatabaseProperties(String fileName) {
        Properties properties = new Properties();
        try {
            final InputStream resourceAsStream = ConnectionFactory.class.getResourceAsStream(fileName);
            if (resourceAsStream == null) {
                throw new IllegalArgumentException("Can't find file: " + fileName);
            } else {
                properties.load(resourceAsStream);

            }
        } catch (IOException e) {
            logger.error("Error during fetching properties for database");
            e.printStackTrace();
            return null;
        }
        return properties;
    }


    public Connection getConnection() {
        try {
            if (dataSource == null) {
                throw new IllegalStateException("Data source is not created yet!");
            }
            return dataSource.getConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}

