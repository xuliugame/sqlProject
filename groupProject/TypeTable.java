import java.util.List;

public class TypeTable {
private DatabaseConnection conn;
	
	public TypeTable(DatabaseConnection conn){
		this.conn=conn;
	}
	
	public String getTypeName(int id){
		Type type = new Type(this.conn);
		type.setTypeId(id);
		boolean res = type.fetch();
		if (res) {
			return type.getTypeName();
		}
		return null;
	}
	
	public int getSubjectId(String name) throws DLException{
		List<String[]> res = this.conn.getData("SELECT typeId FROM _Types WHERE typeName=?",name);
		int size=res.size();
		
		if (size!=1) {
			return -1;
		} else {
			return Integer.valueOf(res.get(0)[0]);
		}
	}
}
