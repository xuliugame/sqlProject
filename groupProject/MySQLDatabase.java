/**
 * A connection to a MySQL database using the MySQL JDBC driver
 */
public class MySQLDatabase extends DatabaseConnection{
    @Override
    public String getDriver() {
        return "com.mysql.jdbc.Driver";
    }

    @Override
    public String getUri(String host, String db, String user, String passwd) {
        return "jdbc:mysql://"+host+"/"+db+"?"+
                (host.equals("localhost")?"useSSL=false&":"")+
                "user="+user+"&password="+passwd;
    }
}
