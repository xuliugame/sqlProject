import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages and simplifies a JDBC connection to a database
 */
public abstract class DatabaseConnection {

    /**
     * Stores the connection
     */
    private Connection conn;

    /**
     * Allows for subclasses to specify the driver name
     * @return The name of the driver
     */
    public abstract String getDriver();

    /**
     * Allows for subclasses to specify the uri format
     * @param host The server to connect to
     * @param db The database name to connect to
     * @param user The username to connect with
     * @param passwd The password to authenticate with
     * @return The URI to connect to the database
     */
    public abstract String getUri(String host,String db,String user,String passwd);

    /**
     * Initiates a connection to the database
     * @param host The server to connect to
     * @param db The database name to connect to
     * @param user The username to connect with
     * @param passwd The password to authenticate with
     */
    public void connect(String host,String db,String user,String passwd) throws DLException{
        String uri=this.getUri(host,db,user,passwd);

        this.loadDriver();

        try{
            this.conn=DriverManager.getConnection(uri);
        }catch(SQLException e) {
            throw new DLException(e,"Could not connect to the database");
        }
    }

    /**
     * Closes the connection to the database
     */
    public void close() throws DLException{
        if(this.conn!=null){
            try{
                this.conn.close();
            }catch(SQLException e){
                throw new DLException(e,"Could not close connection");
            }
        }
    }

    /**
     * Creates a prepared statement and bind parameters
     * @param query The SQL statement to be run
     * @param params The string parameters to be bound to the query
     * @return The created PreparedStatement
     */
    private PreparedStatement prepare(String query,String ...params) throws DLException{
        if(this.conn==null){
            return null;
        }

        PreparedStatement statem=null;
        try{
            statem=this.conn.prepareStatement(query);
        }catch(SQLException e){
            throw new DLException(e,"Could not create prepared statement");
        }

        try{
            for(int index=0; index<params.length; index++){
                if (statem != null) {
                    statem.setString(index+1,params[index]);
                }
            }
        }catch(SQLException e){
            throw new DLException(e,"Could not bind parameters for a prepared statement");
        }

        return statem;
    }
    
    /**
     * Creates a statement
     * @return The created Statement
     */
    private Statement createStatement() throws DLException{
        if(this.conn==null){
            return null;
        }

        Statement statem=null;

        try{
            statem=this.conn.createStatement();
        }catch(SQLException e){
            throw new DLException(e,"Could not create statement");
        }
        return statem;
    }

    /**
     * Parses a resultset into a list of string arrays
     * @param rs The ResultSet to parse
     * @return The results in a list of string arrays
     */
    private List<String[]> parseResults(ResultSet rs) throws DLException{
        List<String[]> arr=null;

        try{
            arr=new ArrayList<>();

            ResultSetMetaData rsm=rs.getMetaData();

            int fields=rsm.getColumnCount();

            while(rs.next()){
                String[] row=new String[fields];
                for(int index=1; index<=fields; index++){
                    row[index-1]=rs.getString(index);
                }
                arr.add(row);
            }
        }catch(SQLException e){
            throw new DLException(e,"Could not parse query results");
        }

        return arr;
    }

    /**
     * Executes a query and returns the results
     * @param query The SQL statement to run
     * @return The results of the query
     */
    public List<String[]> getData(String query) throws DLException{
        Statement statem=this.createStatement();

        if(statem==null){
            return null;
        }

        List<String[]> arr=null;

        try{
            ResultSet rs=statem.executeQuery(query);

            arr=this.parseResults(rs);
        }catch(SQLException e){
            throw new DLException(e,"Could not execute query");
        }finally{
            try {
                statem.close();
            }catch(SQLException e){
                throw new DLException(e,"Could not close query");
            }
        }
        return arr;
    }

    /**
     * Executes a query with parameters and returns the results
     * @param query The SQL statement to run
     * @param params The parameters to be bound to the query
     * @return The results of the query
     */
    public List<String[]> getData(String query,String ...params) throws DLException{
        PreparedStatement statem=this.prepare(query,params);

        if(statem==null){
            return null;
        }

        List<String[]> arr=null;

        try{
            ResultSet rs=statem.executeQuery();

            arr=this.parseResults(rs);
        }catch(SQLException e){
            throw new DLException(e,"Could not execute query");
        }finally{
            try{
                statem.close();
            }catch(SQLException e){
                throw new DLException(e,"Could not close query");
            }
        }

        return arr;
    }

    /**
     * Executes a statement and returns the number of affected rows
     * @param query The SQL statement to run
     * @return The number of affected rows
     */
    public int setData(String query) throws DLException{
        Statement statem=this.createStatement();

        int res=-1;

        if(statem==null){
            return res;
        }

        try{
            res=statem.executeUpdate(query);
        }catch(SQLException e){
            throw new DLException(e,"Could not execute statement");
        }finally{
            try{
                statem.close();
            }catch(SQLException e){
                throw new DLException(e,"Could not close statement");
            }
        }

        return res;
    }

    /**
     * Executes a statement with parameters and returns the number of affected rows
     * @param query The SQL statement to run
     * @param params The parameters to be bound to the query
     * @return The number of affected rows
     */
    public int setData(String query,String ...params) throws DLException{
        PreparedStatement statem=this.prepare(query,params);

        int res=-1;

        if(statem==null){
            return res;
        }
        
        try{
            res=statem.executeUpdate();
        }catch(SQLException e){
            throw new DLException(e,"Could not execute statement");
        }finally{
            try{
                statem.close();
            }catch(SQLException e){
                throw new DLException(e,"Could not close statement");
            }
        }

        return res;
    }

    /**
     * Starts a transaction
     */
    public void startTrans() throws DLException{
        if(this.conn==null){
            return;
        }

        try{
            this.conn.setAutoCommit(false);
        }catch(SQLException e){
            throw new DLException(e,"Could not start transaction");
        }
    }

    /**
     * Ends a transaction
     */
    public void endTrans() throws DLException{
        if(this.conn==null){
            return;
        }

        try{
            this.conn.commit();
            this.conn.setAutoCommit(true);
        }catch(SQLException e) {
            throw new DLException(e,"Could not commit transaction");
        }
    }

    /**
     * Rolls a transaction back and ends it
     */
    public void rollbackTrans() throws DLException{
        if(this.conn==null){
            return;
        }

        try{
            this.conn.rollback();
            this.conn.setAutoCommit(true);
        }catch(SQLException e){
            throw new DLException(e,"Could not roll back transaction");
        }
    }

    /**
     * Attempts to load the database driver
     */
    private void loadDriver() throws DLException{
        try{
            Class.forName(this.getDriver());
        }catch(ClassNotFoundException e){
            throw new DLException(e,"Database driver class not found");
        }
    }
}
