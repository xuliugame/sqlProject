import java.util.List;

public class User extends DataAccessObject {

   public User(DatabaseConnection conn) {
      super(conn);
   }
   
   /**
	 * Gets all the papers associated with a given user ID
	 * @param userId The user to search papers for
	 * @return Array of Paper DAOs that belong to the specified user
    */
   public Paper[] getPapers(int userId) {
      List<String[]> res;
      
      try {
         res = this.conn.getData("SELECT paperId FROM paperauthors WHERE userId=?", String.valueOf(userId));
      } catch(DLException e) {
         return null;
      }
            
      int size = res.size();
		
		if(size <= 0) {
			return null;
		}else{  
         Paper[] paperArray = new Paper[size];
         
         for(int i = 0; i < size; i++) {
            paperArray[i] = new Paper(this.conn);
            paperArray[i].setPaperId(Integer.valueOf(res.get(i)[0]));
            paperArray[i].fetch();
         }
         
			return paperArray;
      }
   }
   
   /**
	 * Returns all info except password for instantiated user
	 * @return String array of all user information
    */
   public String[] getUser() {
      List<String[]> res;
      
      try {
         res = this.conn.getData("SELECT userId, lastName, firstName, email, canReview, expiration, isAdmin, affiliationId FROM users WHERE userId=?", String.valueOf(this.getUserId()));
      } catch(DLException e) {
         return null;
      }
      
      return res.get(0);
   }
   
   /**
	 * If user id already exists in table create new entry, otherwise update specified entry
    * @param lastName Value to add or update for user's last name
    * @param firstName Value to add or update for user's first name
    * @param email Value to add or update for user's email
    * @param pswd Value to add or update for user's password
    * @param affiliation Value to add or update for user's affiliation
    * @return Boolean indicating whether post/put was successful
    */
   public boolean setUser(String lastName, String firstName, String email, String pswd, int affiliation) {
      boolean isNewRecord;
      
      if(this.fetch()) {
         isNewRecord = false;
      } else {
         isNewRecord = true;
      }
      
      this.setLastName(lastName);
      this.setFirstName(firstName);
      this.setEmail(email);
      this.setPswd(pswd);
      this.setAffiliationId(affiliation);

      if(isNewRecord) {
         return this.put();
      } else {
         return this.post();
      }
   }
   
   @Override
   public String getTable() {
      return "users";
   }
    
   @Override   
   public String[] getPrimaryKey() {
      return new String[]{"userId"};
   }
   
   @Override
   public String[] getColumns() {
      return new String[]{"lastName", "firstName", "email", "pswd", "canReview", "expiration", "isAdmin", "affiliationId"};
   }

   /**
	 * Gets the ID for this user
	 * @return int ID of the user
	 */
   public int getUserId() {
      return Integer.parseInt(this.values.get("userId"));
   }
   
    /**
	 * Gets the last name for this user
	 * @return String last name of the user
    */
   public String getLastName() {
      return this.values.get("lastName");
   }
   
    /**
	 * Gets the first name for this user
	 * @return String first name of the user
	 */
   public String getFirstName() {
      return this.values.get("firstName");
   }
   
   /**
	 * Gets the email for this user
	 * @return String email of the user
	 */
   public String getEmail() {
      return this.values.get("email");
   }

   /**
	 * Gets the password for this user
	 * @return String password for this user
	 */
   public String getPswd() {
      return this.values.get("pswd");
   }
   
   /**
	 * Gets whether user has review access
	 * @return int access level for user
	 */
   public int getCanReview() {
      return Integer.parseInt(this.values.get("canReview"));
   }

   /**
	 * Gets the date the user's password will expire
	 * @return String expiration date of user's password
    */
   public String getExpiration() {
      return this.values.get("expiration");
   }
   
   /**
	 * Gets the admin status of the user
	 * @return int 0 or 1 idicating if user is admin or not
	 */
   public int getIsAdmin() {
      return Integer.parseInt(this.values.get("isAdmin"));
   }
   
   /**
	 * Gets the affiliation id of the user
	 * @return int the affiliation id value for the user
	 */
   public int getAffiliationId() {
      return Integer.parseInt(this.values.get("affiliationId"));
   }

   /**
	 * Sets the ID of the user
	 * @param value The new user ID
	 */
   public void setUserId(int value) {
      this.values.put("userId", String.valueOf(value));
   }
   
   /**
	 * Sets the last name of the user
	 * @param value The new last name
	 */
   public void setLastName(String value) {
      this.values.put("lastName", value);
   }
   
   /**
	 * Sets the first name of the user
	 * @param value The new first name
	 */
   public void setFirstName(String value) {
      this.values.put("firstName", value);
   }
   
   /**
	 * Sets the email of the user
	 * @param value The new email
	 */
   public void setEmail(String value) {
      this.values.put("email", value);
   }
   
   /**
	 * Sets the password of the user
	 * @param value The new password
	 */
   public void setPswd(String value) {
      this.values.put("pswd", value);
   }
   
   /**
	 * Sets the review authorization of the user
	 * @param value new review authorization
	 */
   public void setCanReview(int value) {
      this.values.put("canReview", String.valueOf(value));
   }

   /**
	 * Sets the password expiration date for the user
    * @param value The new password expiration date
	 */
   public void setExpiration(String value) {
      this.values.put("expiration", value);
   }
   
   /**
	 * Sets the admin status of the user
	 * @param value 0 for not admin, 1 for admin
    */
   public void setIsAdmin(int value) {
      this.values.put("isAdmin", String.valueOf(value));
   }
   
   /**
	 * Sets the affiliation ID of the user
	 * @param value the new affiliation ID
    */
   public void setAffiliationId(int value) {
      this.values.put("affiliationId", String.valueOf(value));
   }
}