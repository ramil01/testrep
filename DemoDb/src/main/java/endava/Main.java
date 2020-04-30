package endava;

import Model.User;
import com.opencsv.CSVWriter;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static endava.ConnectionSql.*;

public class Main {

    private static Statement stmt;
    private static ResultSet results;

    public static void main(String[] args) throws SQLException, IOException {

        Connection conn = connectionDb();

        String sql_select = "Select * From user_dto";

        stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        results = stmt.executeQuery(sql_select);

        outputInJson();
        results.beforeFirst();

        CSVWriter writer = new CSVWriter(new FileWriter("D://output.csv"));
        ResultSetMetaData mData = results.getMetaData();
        mData.getColumnName(1);

        String line1[] = {mData.getColumnName(1), mData.getColumnName(2), mData.getColumnName(3), mData.getColumnName(4), mData.getColumnName(5)};
        writer.writeNext(line1);
        String data[] = new String[5];

        while (results.next()) {

            data[0] = new Integer(results.getInt("id")).toString();
            data[1] = new Integer(results.getInt("age")).toString();
            data[2] = results.getString("email");
            data[3] = results.getString("first_name");
            data[4] = results.getString("last_name");
            writer.writeNext(data);
        }
        writer.flush();
        System.out.println("Data entered");

    }

    public static void outputInJson() throws IOException, SQLException {
        List<User> userArrayList = new ArrayList<User>();
        while (results.next()) {
            User userObject = new User();
            userObject.setUserId(Integer.valueOf(results.getString("id")));
            userObject.setAge(Integer.valueOf(results.getString("age")));
            userObject.setEmail(results.getString("email"));
            userObject.setFirstName(results.getString("first_name"));
            userObject.setLastName(results.getString("last_name"));
            userArrayList.add(userObject);
        }

        ObjectMapper mapper = new ObjectMapper();
        String JSONOutput = mapper.writeValueAsString(userArrayList);
        System.out.println(JSONOutput);
    }
}



