import java.util.List;

public class SubjectTable{
	private DatabaseConnection conn;
	
	public SubjectTable(DatabaseConnection conn){
		this.conn=conn;
	}
	
	public String getSubjectName(int id){
		Subject sub=new Subject(this.conn);
		sub.setSubjectId(id);
		
		boolean res=sub.fetch();
		if(res){
			return sub.getSubjectName();
		}
		return null;
	}
	
	public int getSubjectId(String name) throws DLException{
		List<String[]> res=this.conn.getData("SELECT subjectId FROM _Subjects WHERE subjectName=?",name);
		int size=res.size();
		
		if(size!=1){
			return -1;
		}else{
			return Integer.valueOf(res.get(0)[0]);
		}
	}
}