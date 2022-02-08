public class Affiliations extends DataAccessObject{
	public Affiliations(DatabaseConnection conn){
		super(conn);
	}

	/**
	 *
	 * @return name of table containing affiliations
	 */
	@Override
	public String getTable(){
		return "_affiliations";
	}

	/**
	 *
	 * @return name of primary key column for relative table
	 */
	@Override
	public String[] getPrimaryKey(){
		return new String[]{"affiliationId"};
	}

	/**
	 *
	 * @return String[] of column names for the relative table
	 */
	@Override
	public String[] getColumns(){
		return new String[]{"affiliationName"};
	}
	
	public int getAffiliationId(){
		return Integer.parseInt(this.values.get("affiliationId"));
	}
	
	public void setAffiliationId(int id){
		this.values.put("affiliationId",String.valueOf(id));
	}
	
	public String getAffiliationName(){
		return this.values.get("affiliationName");
	}
	
	public void setAffiliationName(String name){
		this.values.put("affiliationName",name);
	}
}