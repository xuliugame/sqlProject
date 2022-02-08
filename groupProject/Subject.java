public class Subject extends DataAccessObject {

   public Subject(DatabaseConnection conn) {
      super(conn);
   }
   
   @Override
   public String getTable() {
      return "_subjects";
   }
    
   @Override
   public String[] getPrimaryKey() {
      return new String[]{"subjectId"};
   }
   
   @Override
   public String[] getColumns() {
      return new String[]{"subjectName"};
   }

   /**
	 * Gets the ID for this subject
	 * @return int ID of the subject
	 */
   public int getSubjectId() {
      return Integer.parseInt(this.values.get("subjectId"));
   }
   
   /**
	 * Gets the name of this subject
	 * @return String name of the subject
	 */
   public String getSubjectName() {
      return this.values.get("subjectName");
   }
   
   /**
	 * Sets the ID for this subject
	 * @param value the new subject ID
	 */
   public void setSubjectId(int value) {
      this.values.put("subjectId", String.valueOf(value));
   }
   
   /**
	 * Sets the name for this subject
	 * @param value the new subject name
	 */
   public void setSubjectName(String value) {
      this.values.put("subjectName", value);
   }   
}