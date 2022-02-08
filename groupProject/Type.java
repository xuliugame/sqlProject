/**
 * Represents a Type row
 */
public class Type extends DataAccessObject{
	public Type(DatabaseConnection conn){
		super(conn);
	}
	
	@Override
	public String getTable(){
		return "_types";
	}
	
	@Override
	public String[] getPrimaryKey(){
		return new String[]{"typeId"};
	}
	
	@Override
	public String[] getColumns(){
		return new String[]{"typeName"};
	}
	
	/**
	 * Gets the ID of the type
	 * @return The ID of the type
	 */
	public int getTypeId(){
		return Integer.parseInt(this.values.get("typeId"));
	}
	
	/**
	 * Sets the ID of the row
	 * @param id The new ID
	 */
	public void setTypeId(int id){
		this.values.put("typeId",String.valueOf(id));
	}
	
	/**
	 * Gets the name of the type
	 * @return The name of the row
	 */
	public String getTypeName(){
		return this.values.get("typeName");
	}
	
	/**
	 * Sets the name of the type
	 * @param name The new name for the row
	 */
	public void setTypeName(String name){
		this.values.put("typeName",name);
	}
}
