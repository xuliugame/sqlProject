import java.util.List;

/**
 * Batch operations for the Affiliations table
 */
public class AffiliationsTable extends TableObject<Affiliations>{
	public AffiliationsTable(DatabaseConnection conn){
		super(conn);
	}
	
	public AffiliationsTable(DatabaseConnection conn,List<Affiliations> items){
		super(conn,items);
	}
	
	@Override
	public String getTable(){
		return "_affiliations";
	}
	
	@Override
	public String[] getColumns(){
		return new String[]{"affiliationId","affiliationName"};
	}
	
	@Override
	public Affiliations createItem(String[] row){
		Affiliations aff=new Affiliations(this.conn);
		aff.setAffiliationId(Integer.parseInt(row[0]));
		aff.setAffiliationName(row[1]);
		return aff;
	}
	
	/**
	 * Gets an affiliation name given an id
	 * @param id The affiliationId to look up
	 * @return The name of the affiliation, or null if it doesn't exist
	 */
	public String getAffiliationName(int id){
		Affiliations aff=new Affiliations(this.conn);
		aff.setAffiliationId(id);
		boolean success=aff.fetch();
		if(success){
			return aff.getAffiliationName();
		}
		return null;
	}
	
	/**
	 * Returns the ID associated with an affiliation name if it exists
	 * @param name The affiliation name to look up
	 * @return The ID of the affiliation, or -1 if no such affiliation exists
	 */
	public int getAffiliationId(String name) throws DLException{
		List<String[]> result=this.conn.getData("SELECT affiliationId FROM _Affiliations WHERE affiliationName=?",name);
		int size=result.size();
		
		if(size!=1){
			return -1;
		}else{
			return Integer.parseInt(result.get(0)[0]);
		}
	}
}