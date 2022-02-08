import java.util.List;
import java.util.ArrayList;

/**
 * Generic code for a table abstraction
 */
abstract class TableObject<T extends DataAccessObject>{
	
	protected DatabaseConnection conn;
	
	/**
	 * store all rows in the table
	 */
	protected List<T> list;
	
	/**
	 * Allows subclasses to define the table name
	 * @return The name of the table
	 */
	public abstract String getTable();

	/**
	 * Allows subclasses to define the columns in the table
	 * @return All table columns
	 */
	public abstract String[] getColumns();
	
	/**
	 * Allows subclasses to define how a row object gets created
	 * @param row The result of a query
	 * @return The row object
	 */
	public abstract T createItem(String[] row);

	/**
	 * Constructs TableObject with a connection to a database
	 * @param conn DatabaseConnection object for accessing database
	 */
	public TableObject(DatabaseConnection conn){
		this.conn=conn;
		this.list=new ArrayList<>();
	}

	/**
	 * Constructs TableObject with a connection to a database
	 * @param conn DatabaseConnection object for accessing database
	 * @param items List of Row Access Object items for specified table
	 */
	public TableObject(DatabaseConnection conn,List<T> items){
		this(conn);
		
		this.load(items);
	}
	
	/**
	 * Repopulate the table with the stored rows
	 * @return Whether the operation was successful
	 */
	public boolean post() throws DLException{
		try{
			this.conn.startTrans(); //isolate this operation so failure doesn't result in data loss
		}catch(DLException e){
			return false;
		}
		
		try{
			//remove all entries
			this.conn.setData("DELETE FROM "+this.getTable());
			
			//add everything in this.list
			for(T subject : this.list){
				if(!subject.post()){
					this.conn.rollbackTrans();
					return false;
				}
			}
			
			this.conn.endTrans();
			return true;
		}catch(DLException e){
			this.conn.rollbackTrans();
			return false;
		}
	}
	
	/**
	 * Populate the cache with all rows of the table
	 * @return Whether the operation was successful
	 */
	public boolean fetch(){
		List<String[]> results;
		try{
			results=this.conn.getData("SELECT "+String.join(",",this.getColumns())+" FROM "+this.getTable());
		}catch(DLException e){
			return false;
		}
		
		if(results==null || results.size()==0){
			return false;
		}
		
		for(String[] row : results){
			this.list.add(this.createItem(row));
		}
		return true;
	}
	
	/**
	 * populate the cache with a list
	 * @param items The items to load
	 */
	public void load(List<T> items){
		for(T item:items){
			this.list.add(item);
		}
	}
	
}