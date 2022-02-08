import java.util.List;
import java.util.ArrayList;

public class UserTable extends TableObject<User>{

    /**
     *
     * @param conn database connection to access database
     * @param users List of User objects to be added to this.list
     */
    public UserTable(DatabaseConnection conn, List<User> users){
        super(conn, users);
    }

    /**
     *
     * @return name of table that contains users
     */
   @Override
    public String getTable(){return "users";}

    /**
     *
     * @return String[] of all column names in table
     */
    @Override
    public String[] getColumns() {
        return new String[]{"userId", "lastName", "firstName", "email", "pswd", "canReview", "expiration", "isAdmin", "affiliationId"};
    }

    /**
     * Creates User object based off of a row result of a query
     * @param row The result of a query
     * @return user object with all available data from query result
     */
    @Override
    public User createItem(String[] row) {
        User user = new User(this.conn);
        user.setUserId(Integer.parseInt(row[0]));
        user.setLastName(row[1]);
        user.setFirstName(row[2]);
        user.setEmail(row[3]);
        user.setPswd(row[4]);
        user.setCanReview(Integer.parseInt(row[5]));
        user.setExpiration(row[6]);
        user.setIsAdmin(Integer.parseInt(row[7]));
        user.setAffiliationId(Integer.parseInt(row[8]));
        return user;
    }
}