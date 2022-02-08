import java.util.List;

/**
 * Batch operations for the PaperSubjects table
 */
public class PaperSubjectTable extends TableObject<PaperSubject>{
	public PaperSubjectTable(DatabaseConnection conn){
		super(conn);
	}
	
	public PaperSubjectTable(DatabaseConnection conn,List<PaperSubject> items){
		super(conn,items);
	}
	
	@Override
	public String getTable(){
		return "PaperSubjects";
	}
	
	@Override
	public String[] getColumns(){
		return new String[]{"paperId","subjectId"};
	}
	
	@Override
	public PaperSubject createItem(String[] row){
		PaperSubject ps=new PaperSubject(this.conn);
		ps.setPaperId(Integer.parseInt(row[0]));
		ps.setSubjectId(Integer.parseInt(row[1]));
		return ps;
	}
	
	private int[] listStringToIntArray(List<String[]> input){
		int[] result=new int[input.size()];
		
		for(int i=0;i<result.length;i++){
			result[i]=Integer.parseInt(input.get(i)[0]);
		}
		return result;
	}
	
	/**
	 * Gets all the papers associated with a given subject ID
	 * @param subjectId The subject to search by
	 * @return The paper IDs with that subject
	 */
	public int[] getPapers(int subjectId) throws DLException{
		List<String[]> papers=conn.getData("SELECT paperId FROM PaperSubjects WHERE subjectId=?",String.valueOf(subjectId));
		return this.listStringToIntArray(papers);
	}
	
	/**
	 * Gets all the subjects for a given paper ID
	 * @param paperId The paper to look up
	 * @return The subject IDs or that paper
	 */
	public int[] getSubjects(int paperId) throws DLException{
		List<String[]> subjects=conn.getData("SELECT subjectId FROM PaperSubjects WHERE paperId=?",String.valueOf(paperId));
		return this.listStringToIntArray(subjects);
	}
	
	/**
	 * Gets the names of all the subjects associated with a paper ID
	 * @param paperId The paper to look up
	 * @return The names of all subjects for that paper
	 */
	public String[] getSubjectNames(int paperId) throws DLException{
		int[] subjects=this.getSubjects(paperId); //get subject IDs
		String[] out=new String[subjects.length]; //prepare output array
		
		for(int i=0;i<subjects.length;i++){
			Subject sub=new Subject(this.conn); //use the Subject DAO to translate IDs to names
			sub.setSubjectId(subjects[i]);
			boolean res=sub.fetch();
			if(res){
				out[i]=sub.getSubjectName();
			}
		}
		return out;
	}
}