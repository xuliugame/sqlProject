public class PaperAuthor extends DataAccessObject {

   public PaperAuthor(DatabaseConnection conn) {
      super(conn);
   }
   
   @Override
   public String getTable() {
      return "paperauthors";
   }
    
   @Override   
   public String[] getPrimaryKey() {
      return new String[]{"paperId", "userId"};
   }
   
   @Override
   public String[] getColumns() {
      return new String[]{"displayOrder"};
   }

   /**
	 * Gets the ID for the paper
 	 * @return int the paper ID
	 */
   public int getPaperId() {
      return Integer.parseInt(this.values.get("paperId"));
   }
   
   /**
	 * Gets the ID for the author
 	 * @return int the author ID
	 */
   public int getUserId() {
      return Integer.parseInt(this.values.get("userId"));
   }
   
   /**
	 * Gets the display order for the relationship
  	 * @return int the display order
	 */
   public int getDisplayOrder() {
      return Integer.parseInt(this.values.get("displayOrder"));
   }
   
   /**
	 * Sets the paper ID for the relationship
	 * @param value the new paper ID
	 */
   public void setPaperId(int value) {
      this.values.put("paperId", String.valueOf(value));
   }
   
   /**
	 * Sets the auhtor ID for the relationship
	 * @param value the new author ID
	 */
   public void setUserId(int value) {
      this.values.put("userId", String.valueOf(value));
   }   
   
   /**
	 * Sets the display order for the relationship
	 * @param value the new display order
	 */
   public void setDisplayOrder(int value) {
      this.values.put("displayOrder", String.valueOf(value));
   }   
}