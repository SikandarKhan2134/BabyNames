import java.sql.*;

public class BabyNames {

    public static void main(String[] args) throws SQLException {

        Connection connection = DriverManager.getConnection(
                "jdbc:derby:Resources/babynames", "user", "password");

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "SELECT * FROM BABYNAMES FETCH FIRST 5 ROWS ONLY");

        System.out.println("Getting 5 Baby Names With A Regular Statement");
        printResults(resultSet);

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM BABYNAMES FETCH FIRST ? ROWS ONLY");
        preparedStatement.setInt(1, 5);
        resultSet = preparedStatement.executeQuery();

        printResults(resultSet);

        System.out.println("\nWhat Was The Most Common Boy’s Name In Maryland In 1991?");
        PreparedStatement preparedStatement1 = connection.prepareStatement(
                "SELECT * FROM BABYNAMES WHERE GENDER=? " +
                        "AND US_STATE=? " +
                        "AND DATE_YEAR=? " +
                        "FETCH FIRST 1 ROWs ONLY");
        preparedStatement1.setString(1, "M");
        preparedStatement1.setString(2, "MD");
        preparedStatement1.setInt(3, 1991);
        resultSet = preparedStatement1.executeQuery();
        printResults(resultSet);

        System.out.println("\nWhich Year Were The Most Baby Boy’s Named “Christopher” Born In Any State?");
        PreparedStatement preparedStatement2 = connection.prepareStatement(
                "SELECT * FROM BABYNAMES " +
                        "WHERE NAME = ? " +
                        "ORDER BY NUM_BABIES DESC " +
                        "FETCH FIRST 1 ROWS ONLY"
        );
        preparedStatement2.setString(1, "Christopher");
        resultSet = preparedStatement2.executeQuery();
        printResults(resultSet);

        System.out.println("\nIn Which Year Were The Most Baby Girls Named “Rosemary” Born In Any State?");
        PreparedStatement preparedStatement3 = connection.prepareStatement(
                "SELECT * FROM BABYNAMES " +
                        "WHERE NAME =? " +
                        "AND GENDER =? " +
                        "ORDER BY NUM_BABIES DESC " +
                        "FETCH FIRST 1 ROWS ONLY"
        );
        preparedStatement3.setString(1, "Rosemary");
        preparedStatement3.setString(2, "F");

        resultSet = preparedStatement3.executeQuery();
        printResults(resultSet);

        System.out.println("\nIn 2000, Which Baby Names Were Used More Than Than 500 Times In Maryland");
        PreparedStatement preparedStatement4 = connection.prepareStatement(
                "SELECT * FROM BABYNAMES " +
                        "WHERE DATE_YEAR =? " +
                        "AND NUM_BABIES >? " +
                        "ORDER BY NUM_BABIES ASC"

        );
        preparedStatement4.setInt(1, 2000);
        preparedStatement4.setInt(2, 500);
        resultSet = preparedStatement4.executeQuery();
        printResults(resultSet);


        System.out.println("\nIn 2014, Which State Had The Fewest Boys Named “Xavier”");
        System.out.println("NORTH DAKOTA");
        PreparedStatement preparedStatement5 = connection.prepareStatement(
                "SELECT * FROM BABYNAMES " +
                        "WHERE DATE_YEAR =? " +
                        "AND NAME =? " +
                        "ORDER BY NUM_BABIES ASC " +
                        "FETCH FIRST 1 ROWS ONLY "
        );
        preparedStatement5.setInt(1, 2014);
        preparedStatement5.setString(2, "Xavier");
        resultSet = preparedStatement5.executeQuery();
        printResults(resultSet);

        System.out.println("\nIn 1997, Which State Had The Most Girls Named “Hannah”");
        System.out.println("CALIFORNIA");
        PreparedStatement preparedStatement6 = connection.prepareStatement(
                "SELECT * FROM BABYNAMES " +
                        "WHERE DATE_YEAR =? " +
                        "AND GENDER =? " +
                        "AND NAME =? " +
                        "ORDER BY NUM_BABIES DESC " +
                        "FETCH FIRST 1 ROWS ONLY "
        );
        preparedStatement6.setInt(1, 1997);
        preparedStatement6.setString(2, "F");
        preparedStatement6.setString(3, "Hannah");
        resultSet = preparedStatement6.executeQuery();
        printResults(resultSet);

        System.out.println("\nWrite A Query To Add The Following Row: + " +
                "--id: 10000000 + " +
                "-- name: ‘Joseph’ + " +
                "--date_year: 2016 + " +
                "--gender: ‘M’+ " +
                "--us_state: ‘PA’ + " +
                "--num_babies: ‘476’");

        PreparedStatement preparedStatement7 = connection.prepareStatement(
                "INSERT INTO BABYNAMES (ID, NAME, DATE_YEAR, GENDER, US_STATE, NUM_BABIES) " +
                        "VALUES (?,?,?,?,?,?)");
        preparedStatement7.setInt(1, 10000000);
        preparedStatement7.setString(2, "Joseph");
        preparedStatement7.setInt(3, 2016);
        preparedStatement7.setString(4, "M");
        preparedStatement7.setString(5, "PA");
        preparedStatement7.setInt(6, 476);
        preparedStatement7.execute();
        //printResults(resultSet);


        System.out.println("\nAnswer:  ");

        PreparedStatement ps8 = connection.prepareStatement(
                "SELECT * FROM BABYNAMES " +
                        "WHERE NAME =? " +
                        "AND US_STATE =? " +
                        "AND GENDER =? " +
                        "AND NUM_BABIES =? " +
                        "FETCH FIRST 2 ROW ONLY"
        );

        ps8.setString(1, "Joseph");
        ps8.setString(2, "PA");
        ps8.setString(3, "M");
        ps8.setInt(4, 476);
        resultSet = ps8.executeQuery();
        printResults(resultSet);


        System.out.println("Write a Query to Delete The Row You Just Added.");
        PreparedStatement ps9 = connection.prepareStatement(
                "DELETE FROM BABYNAMES " +
                        "WHERE NAME =? " +
                        "AND GENDER = ? " +
                        "AND US_STATE =? " +
                        "AND DATE_YEAR =? " +
                        "AND NUM_BABIES =?"
        );
        ps9.setString(1, "Joseph");
        ps9.setString(2, "M");
        ps9.setString(3, "PA");
        ps9.setInt(4, 2016);
        ps9.setInt(5, 476);
        ps9.execute();


        System.out.println("\nAnswer:  ");

        PreparedStatement ps10 = connection.prepareStatement(
                "SELECT * FROM BABYNAMES " +
                        "WHERE NAME =? " +
                        "AND GENDER =? " +
                        "ORDER BY DATE_YEAR DESC " +
                        "FETCH FIRST 2 ROW ONLY "

        );

        ps10.setString(1, "Joseph");
        ps10.setString(2, "M");

        resultSet = ps10.executeQuery();
        printResults(resultSet);

    }

    //Print the results from a Baby Names query
    private static void printResults(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            System.out.printf("%d,%s,%d,%s,%s,%d\n",
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getInt(6)
            );
        }
    }
}
