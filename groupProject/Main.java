public class Main{
	public static void main(String[] args){
		DatabaseConnection conn=new MySQLDatabase();
		
		try{
			conn.connect("localhost","CSM","csm","csm");
			
			Type type=new Type(conn);
			TypeBL tbl=new TypeBL(type);
			tbl.setTypeId(1);
			tbl.fetch();
			System.out.println("Name of Type 1 (should be Paper - Applied): "+tbl.getTypeName());
			
			System.out.println("Permission to delete (should be false): "+tbl.delete());
			
			AffiliationsTable afftab=new AffiliationsTable(conn);
			System.out.println("ID of Unknown affiliation (should be 0): "+afftab.getAffiliationId("Unknown"));
			
		}catch(DLException e){
			System.out.println("DLException: "+e.getMessage());
		}
	}
}
