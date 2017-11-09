
package database;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;
import model.Student;

public class StudentDbUtils {

    private Connection conn;
    private OraclePreparedStatement pst;
    private OracleResultSet rs;

    public int getMaxId() {

        if ((conn = DatabaseConnector.databaseConnection()) != null) {
            try {
                String query = "SELECT MAX(id) as std_id from student";
                pst = (OraclePreparedStatement) conn.prepareStatement(query);
                rs = (OracleResultSet) pst.executeQuery();
                if (rs.next()) {
                    return rs.getInt("std_id");
                }
                System.out.println("Max id is  " + rs.getInt("std_id"));
                pst.close();
            } catch (SQLException ex) {
                System.out.println("ERROR  in generating max ID: " + ex);
            }
        }
        return 0;
    }
   

    public Boolean createUser(Student std) throws IOException {
        if ((conn = DatabaseConnector.databaseConnection()) != null) {
            try {
                String query = "INSERT into student values (?,?,?,?,?,?,?,?,?,?)";
                pst = (OraclePreparedStatement) conn.prepareStatement(query);
                pst.setInt(1, std.getId());
                pst.setString(2, std.getName());
                pst.setString(3, std.getAddress());
                pst.setString(4, std.getPhone_no());
                pst.setString(5, std.getRoll_no());
                pst.setString(6, std.getGender());
                pst.setString(7, std.getProgram());
                pst.setString(8, std.getUsername());
                pst.setString(9, std.getPassword());
                pst.setBinaryStream(10, std.getFis());
                pst.setString(11, std.getDob());

                pst.executeQuery();
                return true;

            } catch (SQLException ex) {
                System.out.println("ERROR in SQL : " + ex);
            }
        }
        return false;

    }

    public void updateData(Student std) {
        if ((conn = DatabaseConnector.databaseConnection()) != null) {
            try {
                String query = "UPDATE Student set Name=?, Address=?, roll=?, contact=?,"
                        + " gender=? ,program=? username=?, password= ? where id=" + std.getId();
                pst = (OraclePreparedStatement) conn.prepareStatement(query);
                pst.setString(1, std.getName());
                pst.setString(2, std.getAddress());
                pst.setString(3, std.getPhone_no());
                pst.setString(4, std.getRoll_no());
                pst.setString(5, std.getGender());
                pst.setString(6, std.getProgram());
                pst.setString(7, std.getUsername());
                pst.setString(8, std.getPassword());
                pst.executeUpdate();
                System.out.println("Data Updated Successfully ");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }
    }
    public Boolean deleteUser(Integer id) {
        if ((conn = DatabaseConnector.databaseConnection()) != null) {
            try {
                String query = "DELETE FROM STUDENT WHERE ID=?";

                pst = (OraclePreparedStatement) conn.prepareStatement(query);
                pst.setInt(1, id);
                System.out.println("ID IS : " + id);
                pst.executeQuery();
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(StudentDbUtils.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return false;
    }

//    public void showData(){
//         Student std=(S)table.getSelectionModel().getSelectedItem();
//             String query="select * from StudentDatabase where ID = ?";
//             pst=conn.prepareStatement(query);
//             pst.setString(1, user.getID());
//    }

    
}



    

