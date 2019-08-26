import com.mysql.cj.jdbc.MysqlDataSource;
import lombok.Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Data

public class Main {

    // 1. stworzenie schema: jbdc_students
    // 2.
    // CREATE_TABLE `students`
/*insert into `students` (`name`, `age`, `average`, `alive`)
values
(?, ?, ?, ?);
*/
    private static final String INSERT_QUERY = "insert into `students` (`name`, `age`, `average`, `alive`)\n" +
            "values\n" +
            "(?, ?, ?, ?);\n";
    private static final String CREATE_TABLE_QUERY = "CREATE TABLE if not exists `students`(\n" +
            "`id` int not null auto_increment Primary key,\n" +
            "`name` varchar(255),\n" +
            "`age` int not null,\n" +
            "`average` double not null,\n" +
            "`alive` tinyint not null\n" +
            ");";

    private static final String DB_HOST = "Localhost"; //127.0.0.1
    private static final String DB_PORT = "3306"; //
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "root";
    private static final String DB_NAME = "jbdc_students";

    public static void main(String[] args) {
        MysqlDataSource dataSource = new MysqlDataSource();

        dataSource.setPort(Integer.parseInt(DB_PORT));
        dataSource.setUser(DB_USERNAME);
        dataSource.setServerName(DB_HOST);
        dataSource.setPassword(DB_PASSWORD);
        dataSource.setDatabaseName(DB_NAME);

        try {
            dataSource.setServerTimezone("Europe/Warsaw");
            dataSource.setUseSSL(false);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            Connection connection = dataSource.getConnection();
            System.out.println("HURRRA!");

            Student student = new Student(null, "Pierwszak", 19, 4.9, true);

            try (PreparedStatement statement = connection.prepareStatement(CREATE_TABLE_QUERY)) {
                statement.execute();
            }

            try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
                statement.setString(1, student.getName());
                statement.setInt(2, student.getAge());
                statement.setDouble(3, student.getAverage());
                statement.setBoolean(4, student.isAlive());

                boolean succes = statement.execute();

                if (succes) {
                    System.out.println("SUKCES!");
                }
            }
            ;

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
