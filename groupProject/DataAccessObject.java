import java.util.HashMap;
import java.util.List;

/**
 * Generic code for a data access object
 */
public abstract class DataAccessObject {

    /**
     * The connection to the database
     */
    protected DatabaseConnection conn;

    /**
     * Stores the values for the row
     */
    protected HashMap<String,String> values;

    /**
     * Allows subclasses to define the table name
     * @return The table name
     */
    public abstract String getTable();

    /**
     * Allows subclasses to define the non-primary-key column(s)
     * @return The non primary key column(s)
     */
    public abstract String[] getColumns();

    /**
     * Allows subclasses to define the primary-key column(s)
     * @return The primary key column(s)
     */
    public abstract String[] getPrimaryKey();

    public DataAccessObject(DatabaseConnection conn){
        this.conn=conn;
        this.values=new HashMap<>();
    }

    /**
     * Assuming the primary key values have already been set, get the rest of the values
     * @return Whether the operation was successful
     */
    public boolean fetch(){
        String[] primarykey=this.getPrimaryKey();
        String[] columns=this.getColumns();
        String table=this.getTable();

        //construct the query, get the non primary key column(s)
        String query="SELECT ";
        for(int i=0;i<columns.length;i++){
            if(i!=0){
                query+=",";
            }
            query+=columns[i];
        }
        query+=" FROM "+table+" WHERE ";

        //select by the primary key
        String[] values=new String[primarykey.length];
        for(int i=0;i<primarykey.length;i++){
            if(i!=0){
                query+=" AND ";
            }
            query+=primarykey[i]+"=?";

            values[i]=this.values.get(primarykey[i]);
        }
        
        List<String[]> result;
        try{
            result=this.conn.getData(query,values);
        }catch(DLException e){
            return false;
        }

        if(result.size()<1){
            return false;
        }

        //put the values into the hashmap
        String[] vals=result.get(0);
        for(int i=0;i<columns.length;i++){
            this.values.put(columns[i],vals[i]);
        }
        return true;
    }

    /**
     * Assuming all values have been set, insert the row into the table
     * @return Whether the operation was successful
     */
    public boolean post(){
        String[] primarykey=this.getPrimaryKey();
        String[] columns=this.getColumns();
        String table=this.getTable();

        //construct the query
        String query="INSERT INTO "+table+"(";
        String query2=") VALUES(";
        String[] values=new String[primarykey.length+columns.length];

        //put the values in the query, setting the actual values as parameters
        int pos=0;
        for (String key : primarykey) {
            if (pos != 0) {
                query += ",";
                query2 += ",";
            }
            query += key;
            query2 += "?";
            values[pos]=this.values.get(key);
            pos++;
        }
        for (String column : columns) {
            if (pos != 0) {
                query += ",";
                query2 += ",";
            }
            query += column;
            query2 += "?";
            values[pos]=this.values.get(column);
            pos++;
        }

        query=query+query2+")";

        int result=0;
        try{
            result=this.conn.setData(query,values);
        }catch(DLException e){
            return false;
        }

        return result>0;
    }

    /**
     * Updates the non-primary-key values for the row
     * @return Whether the operation was successful
     */
    public boolean put(){
        String[] primarykey=this.getPrimaryKey();
        String[] columns=this.getColumns();
        String table=this.getTable();

        //construct the query
        String query="UPDATE "+table+" SET ";
        String[] values=new String[columns.length+primarykey.length];

        //update the non primary key columns, set the actual values as parameters
        for(int i=0;i<columns.length;i++){
            if(i!=0){
                query+=",";
            }
            query+=columns[i]+"=?";
            values[i]=this.values.get(columns[i]);
        }

        //specify the row to be altered using the primary key values
        query+=" WHERE ";
        for(int i=0;i<primarykey.length;i++){
            if(i!=0){
                query+=" AND ";
            }
            query+=primarykey[i]+"=?";
            values[i+columns.length]=this.values.get(primarykey[i]);
        }

        int result=0;
        try{
            result=this.conn.setData(query,values);
        }catch(DLException e){
            return false;
        }

        return result>0;
    }

    /**
     * Delete a row based on the primary key values
     * @return Whether the operation was successful
     */
    public boolean delete(){
        String[] primarykey=this.getPrimaryKey();
        String table=this.getTable();

        //construct the query
        String query="DELETE FROM "+table+" WHERE ";
        String[] values=new String[primarykey.length];

        //add the primary key values as parameters
        for(int i=0;i<primarykey.length;i++){
            if(i!=0){
                query+=" AND ";
            }
            query+=primarykey[i]+"=?";
            values[i]=this.values.get(primarykey[i]);
        }

        int result=0;
        try{
            result=this.conn.setData(query,values);
        }catch(DLException e){
            return false;
        }

        return result>0;
    }

}
