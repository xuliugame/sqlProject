import java.util.List;

public class PaperTable extends TableObject<Paper> {
   public PaperTable(DatabaseConnection conn){
		super(conn);
	}
	
	public PaperTable(DatabaseConnection conn, List<Paper> papers){
		super(conn, papers);
	}
   
   @Override
	public String getTable(){
		return "Papers";
	}
	
	@Override
	public String[] getColumns(){
		return new String[]{"paperId", "title", "abstract", "track", "status", "submissionType", "submitterId", "fileId", "tentativeStatus"};
	}
	
	@Override
	public Paper createItem(String[] row){
		Paper paper = new Paper(this.conn);
		paper.setPaperId(Integer.parseInt(row[0]));
		paper.setTitle(row[1]);
      paper.setAbstract(row[2]);
      paper.setTrack(row[3]);
      paper.setStatus(row[4]);
      paper.setSubmissionType(Integer.parseInt(row[5]));
      paper.setSubmitterId(Integer.parseInt(row[6]));
      paper.setFileId(row[7]);
      paper.setTentativeStatus(row[8]);
		return paper;
	}
   
   /**
     * Returns the title for a for a specified paper ID
     * @param id ID of the paper you want the title for
     * @return String title of the book, null if no paper found
     */
	public String getTitle(int id) {
		Paper paper = new Paper(this.conn);
		paper.setPaperId(id);
		
		boolean res = paper.fetch();
		if(res) {
			return paper.getTitle();
		}
		return null;
	}
	
   /**
     * Returns the IDs of papers that match a specified title
     * @param title The title you want paper IDs for
     * @return Array of paper IDs that match the specified title, null if no papers found
     */
	public int[] getPaperId(String title) {
      List<String[]> res;
      
      try {
         res = this.conn.getData("SELECT paperId FROM papers WHERE title=?", title);
      } catch(DLException e) {
         return null;
      }

		int size = res.size();
		if(size <= 0) {
			return null;
		}
              
      int[] idArray = new int[size];     
      for(int i = 0; i < size; i++) {
         idArray[i] = Integer.valueOf(res.get(i)[0]);
      }
         
		return idArray;
	}
}