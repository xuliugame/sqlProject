/**
 * This table uses both its columns as a composite primary key, the main use of it will be
 * the boolean return value of fetch() that indicates whether a row with those values exists or not
 */
public class PaperSubject extends DataAccessObject{
	public PaperSubject(DatabaseConnection conn){
		super(conn);
	}
	
	@Override
	public String getTable(){
		return "PaperSubjects";
	}
	
	@Override
	public String[] getPrimaryKey(){
		return new String[]{"paperId","subjectId"};
	}
	
	@Override
	public String[] getColumns(){
		return new String[]{};
	}
	
	public int getPaperId(){
		return Integer.parseInt(this.values.get("paperId"));
	}
	
	public void setPaperId(int id){
		this.values.put("paperId",String.valueOf(id));
	}
	
	public int getSubjectId(){
		return Integer.parseInt(this.values.get("subjectId"));
	}
	
	public void setSubjectId(int id){
		this.values.put("subjectId",String.valueOf(id));
	}
}